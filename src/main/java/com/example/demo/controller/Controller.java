package com.example.demo.controller;

import com.example.demo.Dto.*;
import com.example.demo.model.*;
import com.example.demo.service.*;
import com.example.demo.service.Jwt.JwtService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api")
public class Controller {

    private final UserService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StockService stockService;
    private final WarehouseService warehouseService;
    private final WarehouseStockService warehouseStockService;
    private final WarehouseTransferService warehouseTransferService;
    private final ClientService clientService;
    private final BalanceService balanceService;
    private final PurchaseService purchaseService;
    private final ExpenseInvoiceService expenseInvoiceService;
    private final UserService userService;
    private final InformationCodeService informationCodeService;
    public final BankAccountInfoService bankAccountInfoService;
    public final BalanceTransferService balanceTransferService;
    private final TransactionService transactionService;
    private final ReportService reportService;

    public Controller(UserService service, JwtService jwtService, AuthenticationManager authenticationManager, StockService stockService, WarehouseService warehouseService, WarehouseStockService warehouseStockService, WarehouseTransferService warehouseTransferService, ClientService clientService, ExpenseInvoiceService expenseInvoiceService, InformationCodeService informationCodeService, BankAccountInfoService bankAccountInfoService, BalanceService balanceService, PurchaseService purchaseService, UserService userService, BalanceTransferService balanceTransferService, TransactionService transactionService, ReportService reportService) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.stockService = stockService;
        this.warehouseService = warehouseService;
        this.warehouseStockService = warehouseStockService;
        this.warehouseTransferService = warehouseTransferService;
        this.clientService = clientService;
        this.expenseInvoiceService = expenseInvoiceService;
        this.informationCodeService = informationCodeService;
        this.bankAccountInfoService = bankAccountInfoService;
        this.balanceService = balanceService;
        this.purchaseService = purchaseService;
        this.userService = userService;
        this.balanceTransferService = balanceTransferService;
        this.transactionService = transactionService;
        this.reportService = reportService;
    }

    //login
    //Authentication Endpoints
    @PostMapping("/addNewUser")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {
        if (service.addUser(userInfo)) {
            return ResponseEntity.ok("User added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.");
        }
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequestDto authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername()) + ";" + authRequest.getUsername();
        } else {
            return ("Invalid user request !");
        }
    }

    @GetMapping("/getExpired")
    public ResponseEntity<String> getExpired() {
        return ResponseEntity.ok("true");
    }

    @GetMapping("/getClientCode")
    public ResponseEntity<Long> getClientCode() {
        long clientCode = clientService.getClientCode();
        return ResponseEntity.ok(clientCode);
    }

    // Stock ekleme
    @PostMapping("/stocks")
    public ResponseEntity<String> addStock(@RequestBody StockDto stock) {
        String result = stockService.addStock(stock);
        if ("Success".equals(result)) {
            return ResponseEntity.ok("Stock added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    // Warehouse ekleme
    @PostMapping("{copy}/warehouse")
    public ResponseEntity<String> addWarehouse(@RequestBody Warehouse warehouse, @PathVariable boolean copy) {
        warehouseService.addWarehouse(warehouse, copy);
        return ResponseEntity.ok("Warehouse added successfully.");
    }

    // Warehouse edit
    @PutMapping("/warehouse/{copy}/{warehouseId}")
    public ResponseEntity<String> updateWarehouse(@PathVariable Long warehouseId, @PathVariable boolean copy, @RequestBody WarehouseEditDto updatedWarehouse) {
        if (warehouseService.updateWarehouse(warehouseId, updatedWarehouse, copy)) {
            return ResponseEntity.ok("Warehouse updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update warehouse.");
        }
    }

    @GetMapping("/getWarehouseName")
    public ResponseEntity<List<String>> getWarehouseNames() {
        List<String> warehouseNames = warehouseService.getWarehouseNames();
        return ResponseEntity.ok(warehouseNames);
    }

    // Warehouse alma
    @GetMapping("/getWarehouse")
    public ResponseEntity<List<Warehouse>> getWarehouses() {
        List<Warehouse> warehouses = warehouseService.getWarehouse();
        return ResponseEntity.ok(warehouses);
    }


    // warehouse silme
    //client silme
    @PostMapping("/deleteWarehouse")
    public ResponseEntity<Boolean> deleteWarehouse(@RequestBody DeleteDto deleteDto) {
        //  System.out.println(deleteDto.getId());
        boolean transferResult = warehouseService.deleteWarehouse(deleteDto.getId());

        if (transferResult == true) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transferResult);
        }
    }

    //client ekleme
    @PostMapping("/clients")
    public ResponseEntity<String> addClient(@RequestBody ClientDto client) {
        if (clientService.addClient(client)) {
            return ResponseEntity.ok("Client added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error of addition client");
        }
    }

    // client düzenleme
    @PutMapping("/clients/{clientId}")
    public ResponseEntity<String> updateClient(@PathVariable Long clientId, @RequestBody ClientDto clientDto) {
        if (clientService.updateClient(clientId, clientDto)) {
            return ResponseEntity.ok("Client updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update client");
        }
    }

    //client silme
    @PostMapping("/clientDelete")
    public ResponseEntity<Boolean> clientDelete(@RequestBody DeleteDto deleteDto) {
        //  System.out.println(deleteDto.getId());
        boolean transferResult = clientService.deleteClient(deleteDto.getId());

        if (transferResult == true) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transferResult);
        }
    }


    //transactionEkleme
    @PostMapping("/transactions")
    public ResponseEntity<String> addTransaction(@RequestBody TransactionDto transaction) {
        if (transactionService.addTransaction(transaction)) {
            return ResponseEntity.ok("Transaction added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error of addition Transaction");
        }
    }

    //InformationCode sorguları
    @PostMapping("/information-codes")
    public ResponseEntity<String> addInformationCode(@RequestBody InformationCodeDto informationCodeDto) {
        if (informationCodeService.addInformationCode(informationCodeDto)) {
            return ResponseEntity.ok("Information Code updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating Information Code");
        }
    }

    //Add bank Account Info
    @PostMapping("/bankAccountInfos")
    public ResponseEntity<String> addBankAccountInfo(@RequestBody BankAccountInfoDto bankAccountInfo) {
        if (bankAccountInfoService.addBankAccountInfo(bankAccountInfo)) {
            return ResponseEntity.ok("Bank account infos succeed");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bank account infos error");
        }
    }

    //Add balance
    @PostMapping("/balances")
    public ResponseEntity<String> addBalance(@RequestBody BalanceDto balance, @RequestParam("name") String name) {
        if (balanceService.addBalance(balance, name)) {
            return ResponseEntity.ok("Balance succeed");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Balance error");
        }
    }

    //    @PostMapping("/balance/transfer")
//    public ResponseEntity<String> balancetransfer(@RequestBody BalanceTransferDto balanceTransferDto) {
//        String transferResult = balanceTransferService.transfer(balanceTransferDto);
//
//        if (transferResult != null) {
//            // Transfer başarılıysa
//            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
//        } else {
//            // Transfer başarısızsa veya bir hata oluştuysa
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hata transfer");
//        }
//
//    }
    @GetMapping("/getBalanceTransferByPage")
    public ResponseEntity<Page<BalanceTransfer>> getBalanceTransfers(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size
            , @RequestParam("keyword") String keyword) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());


        Page<BalanceTransfer> balance = balanceTransferService.getAllBalanceTransfers(pageable, keyword); // Tüm ürünleri getir

        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    // Quantity ekleme
    @PatchMapping("/{stockId}/updateQuantityIn")
    public ResponseEntity<String> updateQuantityIn(@PathVariable Long stockId, @RequestParam Integer quantityIn) {

        if (warehouseStockService.updateQuantityIn(stockId, quantityIn)) {
            return ResponseEntity.status(HttpStatus.OK).body("WarehouseStock updateQuantityIn updated:" + stockId);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updateQuantityIn:" + stockId);
    }

    @PatchMapping("/{clientID}/updateBalance")
    public ResponseEntity<String> updateBalance(@PathVariable Long clientID, @RequestParam String paymentType, @RequestParam BigDecimal value) {
        if (balanceService.updateBalance(clientID, paymentType, value)) {
            return ResponseEntity.status(HttpStatus.OK).body("Updated UpdateBalance");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error UpdateBalance");
    }

    @PatchMapping("/{clientID}/updateBalance2")
    public ResponseEntity<String> updateBalance2(@PathVariable Long clientID, @RequestParam String paymentType, @RequestParam BigDecimal value) {
        if (balanceService.updateBalance2(clientID, paymentType, value)) {
            return ResponseEntity.status(HttpStatus.OK).body("Updated UpdateBalance");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error UpdateBalance");
    }

    // Quantity çıkarma
    @PatchMapping("/{stockId}/updateQuantityOut")
    public ResponseEntity<String> updateQuantityOut(@PathVariable Long stockId, @RequestParam Integer quantityOut) {

        if (warehouseStockService.updateQuantityOut(stockId, quantityOut)) {
            return ResponseEntity.status(HttpStatus.OK).body("WarehouseStock updateQuantityOut updated:" + stockId);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updateQuantityOut:" + stockId);
    }

    // bütün stocklar alma şuan gereksiz gibi
//    @GetMapping("/getStocks")
//    public ResponseEntity<List<Stock>> getAllStocks() {
//        List<Stock> stocks = stockService.findAllActiveStocks();
//
//        if (stocks.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.ok(stocks);
//        }
//    }
//    @GetMapping("/getStocksByPage")
//    public ResponseEntity<List<Stock>> getUrunler(@RequestParam(defaultValue = "0") int page,
//                                                  @RequestParam(defaultValue = "10") int size) {
//        List<Stock> urunler = stockService.findAllActiveStocks(); // Tüm ürünleri getir
//
//        int startIndex = page * size;
//        int endIndex = Math.min(startIndex + size, urunler.size());
//
//        // İstenen sayfadaki ürünleri al
//        List<Stock> istenenUrunler = urunler.subList(startIndex, endIndex);
//
//        return new ResponseEntity<>(istenenUrunler, HttpStatus.OK);
//    }  yeniii
    @GetMapping("/getStocksByPage")
    public ResponseEntity<Page<Stock>> getUrunler(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {


        Pageable pageable = PageRequest.of(page, size, Sort.by( "statusStock","stockName").ascending());
        Page<Stock> urunler = stockService.findAllActiveStocks(pageable);

        return new ResponseEntity<>(urunler, HttpStatus.OK);
    }

    @PostMapping("/setStatus")
    public ResponseEntity<String> setStatus(@RequestParam("stockId") Long stockId) {
       boolean status;
        try {

            status= stockService.setStatus(stockId);


        }
        catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
        if (!status) {
            return new ResponseEntity<>("Status set active ",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Status set deactivate",HttpStatus.OK);
        }


    }

    @GetMapping("/getStocksBySearch")
    public ResponseEntity<Page<Stock>> getUrunler(
            @RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by( "statusStock", "stockCode").ascending());
        Page<Stock> urunler = stockService.searchItems(keyword, pageable);
        //   System.out.println(urunler);
        return new ResponseEntity<>(urunler, HttpStatus.OK);
    }

    //  stocklar alma warehouse transfer için yapılan sadece id ve isim
    @GetMapping("/getStocksById")
    public ResponseEntity<Page<StockWarehouseDto>> getstockwithid(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "6") int size,
                                                                  @RequestParam("warehouse_id") Long warehouse_id
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by( "statusStock","stockName").ascending());

        Page<StockWarehouseDto> stocks = stockService.getStockWithId(warehouse_id, pageable);

        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/getStocksByIdSearch")
    public ResponseEntity<Page<StockWarehouseDto>> getStocksByIdSearch(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "6") int size,
                                                                       @RequestParam("warehouse_id") Long warehouse_id,
                                                                       @RequestParam("keyword") String keyword) {

        Pageable pageable = PageRequest.of(page, size, Sort.by( "statusStock", "stockCode").ascending());

        Page<StockWarehouseDto> stocks = stockService.getStocksByIdSearch(warehouse_id, pageable, keyword);

        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    //    @GetMapping("/getStockWithIdProduct")
//    public ResponseEntity<List<Stock>> getStockWithIdProduct(@RequestParam("warehouse_id") Long warehouse_id) {
//        List<Stock> stocks  = stockService.getStockWithIdProduct(warehouse_id);
//
//        if (stocks.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.ok(stocks);
//        }
//    }
    @GetMapping("/getStockWithIdProductByPage")
    public ResponseEntity<Page<Stock>> getStockWithIdProductByPage(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size,
                                                                   @RequestParam("warehouse_id") Long warehouse_id
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by( "statusStock","stockName").ascending());
        Page<Stock> stock = stockService.getStockWithIdProduct(warehouse_id, pageable);


        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @GetMapping("/getStockWithIdProductByPageSearch")
    public ResponseEntity<Page<Stock>> getStockWithIdProductByPage(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size,
                                                                   @RequestParam("warehouse_id") Long warehouse_id,
                                                                   @RequestParam("keyword") String keyword) {

        Pageable pageable = PageRequest.of(page, size, Sort.by( "statusStock","stockName").ascending());
        Page<Stock> stock = stockService.searchItemswithid(warehouse_id, pageable, keyword);


        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    //stock update
    @PostMapping("/stockUpdate")
    public ResponseEntity<Boolean> addPurchase(@RequestBody StockUpdateDto stockUpdateDto) {
        boolean transferResult = stockService.stockUpdate(stockUpdateDto);

        if (transferResult == true) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transferResult);
        }
    }

    //stock update
    @PostMapping("/warehouseStockUpdate")
    public ResponseEntity<Boolean> stockUpdate(@RequestBody StockWarehouseUpdateDto stockWarehouseUpdateDto) {
        boolean transferResult = warehouseStockService.updateWarehouseStock(stockWarehouseUpdateDto);

        if (transferResult == true) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transferResult);
        }
    }

    //warehousestock delete
    @PostMapping("/warehouseStockDelete")
    public ResponseEntity<Boolean> stockUpdate(@RequestBody DeleteDto deleteDto) {
        boolean transferResult = warehouseStockService.deleteWarehouseStock(deleteDto);

        if (transferResult == true) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transferResult);
        }
    }

    //product delete
    @PostMapping("/productDelete")
    public ResponseEntity<Boolean> productDelete(@RequestBody DeleteDto deleteDto) {
        //  System.out.println(deleteDto.getId());
        boolean transferResult = stockService.deleteStock(deleteDto.getId());

        if (transferResult == true) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transferResult);
        }
    }

    @GetMapping("getBalanceWithClientID")
    public ResponseEntity<Balance> findBalanceByClientID(@RequestParam("ClientID") Long ClientID) {
        Balance balances = balanceService.findBalanceByClientID(ClientID);
        if (balances == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(balances);
        }
    }

    //  kullanıcıdan alınan  listesi ödeme için
    @GetMapping("/getPurchaseInvoiceClient")
    public ResponseEntity<List<PurchaseDto2>> getClientExpensesInvoicewithid(@RequestParam("client_id") Long client_id) {
        List<PurchaseDto2> purchases = purchaseService.getPurchaseWithId(client_id);

        if (purchases.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(purchases);
        }
    }

    @GetMapping("/getPurchaseInvoiceClientByPage")
    public ResponseEntity<Page<PurchaseDto2>> getPurchaseInvoiceClientByPage(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size,
                                                                             @RequestParam("client_id") Long client_id,
                                                                             @RequestParam("keyword") String keyword) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<PurchaseDto2> purchase = purchaseService.getPurchaseWithIdAndPages(pageable, keyword, client_id); // Tüm ürünleri getir
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    //  kullanıcıdan  satılanların listesi ödeme için,
    @GetMapping("/getClientSalesInvoicewithid")
    public ResponseEntity<Page<ExpenseInvoiceDto2>> getClientSalesInvoicewithid(@RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                @RequestParam("client_id") Long client_id,
                                                                                @RequestParam("keyword") String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<ExpenseInvoiceDto2> expenses = expenseInvoiceService.getExpenseWithId(pageable, keyword, client_id);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }



    @GetMapping("getClientByName")
    public ResponseEntity<Client> findClientByName(@RequestParam("name") String name) {
        Client clients = clientService.getClientWithName(name);
        if (clients == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(clients);
        }
    }

    // bütün warehouseları alma
//    @GetMapping("/getWarehouseStock")
//    public ResponseEntity<List<WarehouseStock>> getAllWarehouseStock() {
//        List<WarehouseStock> stocks = warehouseStockService.getAllWarehouseStock();
//        if (stocks.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.ok(stocks);
//        }
//    }
    //
    @GetMapping("/getWarehouseStockByPage")
    public ResponseEntity<Page<WarehouseStock>> getWarehouseStockByPage(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("stock.statusStock","stock.stockName").ascending());

        Page<WarehouseStock> stocks = warehouseStockService.getAllWarehouseStock(pageable);
        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(stocks, HttpStatus.OK);
        }
    }

    @GetMapping("/getWarehouseStockBySearch")
    public ResponseEntity<Page<WarehouseStock>> getWarehouseStockBySearch(
            @RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {        Pageable pageable = PageRequest.of(page, size, Sort.by("stock.statusStock","stock.stockName").ascending());

        Page<WarehouseStock> stocks = warehouseStockService.searchItems(keyword, pageable);
        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(stocks, HttpStatus.OK);
        }

    }

    @GetMapping("/getWarehouseStockBySearchAndWarehouse")
    public ResponseEntity<Page<WarehouseStock>> getWarehouseStockBySearchAndWarehouse(
            @RequestParam("keyword") String keyword,
            @RequestParam("warehouse") String wharehouse,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("stock.statusStock","stock.stockName").ascending());
        Page<WarehouseStock> stocks = warehouseStockService.searchItemsW(keyword, wharehouse, pageable);
        if (stocks.isEmpty()) {

            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(stocks, HttpStatus.OK);
        }

    }

    //  warehousedaki stockları alma id ile
    //  warehousedaki stockları alma id ile
    @GetMapping("/getWarehouseStockWithId")
    public ResponseEntity<List<WarehouseStock>> getWithIdWarehouseStock(@RequestParam("warehouse_id") Long warehouse_id) {
        List<WarehouseStock> stocks = warehouseStockService.getWithIdWarehouseStock(warehouse_id);
        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(stocks);
        }
    }


    // get all Clients
//    @GetMapping("/getClients")
//    public ResponseEntity<List<Client>> getAllClients() {
//        List<Client> clients = clientService.getAllClients();
//
//        if (clients.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.ok(clients);
//        }
//    }
    @GetMapping("getClientsByPage")
    public ResponseEntity<Page<Client>> getClientsByPage(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("Name").ascending());
        Page<Client> client = clientService.getAllClients(pageable);

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/getClientsBySearch")
    public ResponseEntity<Page<Client>> getclientssearch(
            @RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Client> urunler = clientService.searchItems(keyword, pageable);
        //  System.out.println(urunler);
        return new ResponseEntity<>(urunler, HttpStatus.OK);
    }

    //Transaction Alma
    @GetMapping("getTransactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(transactions);
        }
    }

    //Balance Alma
    @GetMapping("/getBalances")
    public ResponseEntity<List<Balance>> getAllBalances() {
        List<Balance> balances = balanceService.getAllBalances();
        if (balances.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(balances);
        }
    }

    //Purchase satın alma faturası eklenecek
    @GetMapping("/getPurchases")
    public ResponseEntity<List<PurchaseDto2>> getAllPurchases() {
        List<PurchaseDto2> purchases = purchaseService.getAllPurchases();
        if (purchases.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(purchases);
        }
    }

    @GetMapping("/getPurchasesByPage")
    public ResponseEntity<Page<PurchaseDto2>> getPurchasesByPage(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam("keyword") String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());

        Page<PurchaseDto2> purchase = purchaseService.getAllPurchasesInvoices(pageable, keyword); // Tüm ürünleri getir

        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    //purchase oluşturma
    @PostMapping("/purchase")
    public ResponseEntity<PurchaseDto2> addPurchases(@RequestBody PurchaseDto purchaseDto) {

        PurchaseDto2 transferResult = purchaseService.addPurchase(purchaseDto);

        if (transferResult!=null) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transferResult);
        }
    }

    //sales satın alma faturası eklenecek   gerek kalmadı eskidin artık
//    @GetMapping("/getSales")
//    public ResponseEntity<List<ExpenseInvoiceDto2>> getAllSales() {
//        List<ExpenseInvoiceDto2> Sales = expenseInvoiceService.getAllExpenseInvoice();
//        if (Sales.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.ok(Sales);
//        }
//    }
    @GetMapping("/getSalesByPage")
    public ResponseEntity<Page<ExpenseInvoiceDto2>> getSalesByPage(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size
            , @RequestParam("keyword") String keyword
    ) {

        // Tüm ürünleri getir
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<ExpenseInvoiceDto2> sale = expenseInvoiceService.getAllExpenseInvoice(pageable, keyword);


        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    //sales oluşturma
    @PostMapping("/Sales")
    public ResponseEntity<ExpenseInvoiceDto2> addPurchase(@RequestBody ExpenseInvoiceDto expenseInvoiceDto) {
        //  System.out.println(expenseInvoiceDto);

        ExpenseInvoiceDto2 transferResult = expenseInvoiceService.addExpenseInvoice(expenseInvoiceDto);

        if (transferResult!=null) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transferResult);
        }
    }

    //    warehouse stock tarnsfer
    @PostMapping("/warehouseStock/transfer")
    public ResponseEntity<String> transfer(@RequestBody WarehouseTransferDto warehouseTransferDto) {
        String transferResult = warehouseTransferService.transfer(warehouseTransferDto);

        if (Objects.equals(transferResult, "Transfer successful")) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transferResult);
        }
    }

    // get waiting warehouse transfer
    @GetMapping("/warehouseStock/get_waiting_transfer")
    public ResponseEntity<List<Map<String, String>>> getwaitngtransfer() {
        List<WarehouseTransfer> warehouseTransfers = warehouseTransferService.getallwaitingtransfer();
        List<Map<String, String>> transferNames = new ArrayList<>();
        for (WarehouseTransfer transfer : warehouseTransfers) {
            Map<String, String> names = new HashMap<>();
            names.put("id", transfer.getWarehouseTransferId().toString());
            names.put("source", transfer.getSource().getName());
            names.put("target", transfer.getTarget().getName());
            names.put("quantity", transfer.getQuantity().toString());
            names.put("comment", transfer.getComment());
            names.put("approvelstatus", transfer.getApprovalStatus());
            names.put("date", transfer.getDate().toString());
            names.put("stockName", transfer.getStockName());
            names.put("stockCode", transfer.getStockCode());
            transferNames.add(names);
        }
        return ResponseEntity.ok(transferNames);
    }

    // approvelStatus change
    @PatchMapping("/{warehouse_transfer_id}/approvelStatus")
    public ResponseEntity<String> change_approvelStatus(@PathVariable Long warehouse_transfer_id, @RequestParam String status) {
        if (Objects.equals(status, "onay")) {
            warehouseTransferService.change_status(warehouse_transfer_id, status);
            return ResponseEntity.ok("onaylandı");
        } else if (Objects.equals(status, "red")) {
            warehouseTransferService.change_status(warehouse_transfer_id, status);
            return ResponseEntity.ok("Rejected. The amount has been transferred back to the source.");
        } else
            return ResponseEntity.badRequest().body("Wrong amount");

    }

    @GetMapping("/getDailyExpenses")
    public List<Object> getDailyExpenses(@RequestParam("date") LocalDateTime date) {
        return reportService.getDailyExpenses(date);
    }

    @GetMapping("/getWeeklyPurchaseInvoices")
    public List<Object> getWeeklyPurchaseInvoices(@RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate) {
        return reportService.getWeeklyPurchaseInvoices(startDate, endDate);
    }

    @GetMapping("/getStockCode")
    public long getStockCode() {
        return stockService.getStockCode();
    }

    //  stocklar  sales remaining
    @GetMapping("/getStocksRemainigById")
    public ResponseEntity<String> getStocksRemainigById(@RequestParam("stock_id") Long warehouse_id) {
        String stocks = stockService.getStocksRemainigById(warehouse_id);

        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(stocks);
        }
    }

    @GetMapping("/getUserInfos")
    public ResponseEntity<String> getUserInfos() {
        String currentUsername = getCurrentUsername();
        Optional<UserInfo> userInfoOptional = userService.findByUsername(currentUsername);

        if (userInfoOptional.isPresent()) {
            UserInfo userInfo = userInfoOptional.get();

            String name = userInfo.getName();
            String email = userInfo.getEmail();
            String responseJson = "{\"name\":\"" + name + "\",\"email\":\"" + email + "\"}";
            return ResponseEntity.ok(responseJson);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/editUsername")
    public ResponseEntity<String> editUsername(@RequestBody UserInfoEditDto editUsernameRequest) {
        String currentUsername = getCurrentUsername();
        boolean result = userService.setUsername(currentUsername, editUsernameRequest.getInfo1(), editUsernameRequest.getInfo2());

        //  System.out.println(result);
        if (result) {
            return ResponseEntity.ok("Username and Email updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/editPassword")
    public ResponseEntity<String> editPassword(@RequestBody UserInfoEditDto editPasswordRequest) {
        String currentUsername = getCurrentUsername();
        boolean result = userService.setPassword(currentUsername, editPasswordRequest.getInfo2());
        if (result) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount() {
        String currentUsername = getCurrentUsername();
        boolean result = userService.deleteUser(currentUsername);
        if (result) {
            return ResponseEntity.ok("User account deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found");
        }
    }


    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
