package com.example.demo.controller;

import com.example.demo.Dto.*;
import com.example.demo.model.*;
import com.example.demo.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
public class Controller {
    private final StockService stockService;
    private final WarehouseService warehouseService;
    private final WarehouseStockService warehouseStockService;
    private final WarehouseTransferService warehouseTransferService;
    private final ClientService clientService;
    private final BalanceService balanceService;
    private final PurchaseService purchaseService;
    private final ExpenseInvoiceService expenseInvoiceService;

    private final InformationCodeService informationCodeService;
    public final BankAccountInfoService bankAccountInfoService;
    public final BalanceTransferService balanceTransferService;
    private final TransactionService transactionService;
    private final ReportService reportService;
    public Controller(StockService stockService, WarehouseService warehouseService, WarehouseStockService warehouseStockService, WarehouseTransferService warehouseTransferService, ClientService clientService, ExpenseInvoiceService expenseInvoiceService, InformationCodeService informationCodeService, BankAccountInfoService bankAccountInfoService, BalanceService balanceService, PurchaseService purchaseService, BalanceTransferService balanceTransferService, TransactionService transactionService, ReportService reportService) {
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
        this.balanceTransferService = balanceTransferService;
        this.transactionService = transactionService;
        this.reportService = reportService;
    }

    // Stock ekleme
    @PostMapping("/stocks")
    public ResponseEntity<String> addStock(@RequestBody StockDto stock) {
        if (stockService.addStock(stock)) {
            return ResponseEntity.ok("Stock added successfully");
        } else {
            return ResponseEntity.ok("hata");
        }
    }

    // Warehouse ekleme
    @PostMapping("/warehouse")
    public ResponseEntity<String> addStock(@RequestBody Warehouse Warehouse) {
        warehouseService.addWarehouse(Warehouse);
        return ResponseEntity.ok("Stock added successfully");
    }

    // Warehouse alma
    @GetMapping("/getWarehouse")
    public ResponseEntity<List<Warehouse>> getWarehouse() {
        List<Warehouse> Warehouse = warehouseService.getWarehouse();
        return ResponseEntity.ok(Warehouse) ;
    }


    //client ekleme

    @PostMapping("/clients")
    public ResponseEntity<String> addClient(@RequestBody ClientDto client){
      if(clientService.addClient(client)){
      return ResponseEntity.ok("Client added successfully");
      }else{
          return ResponseEntity.ok("Error of addition client");
      }
    }
    //transactionEkleme
    @PostMapping("/transactions")
    public ResponseEntity<String> addTransaction(@RequestBody TransactionDto transaction){
        if(transactionService.addTransaction(transaction)){
            return ResponseEntity.ok("Transaction added successfully");
        }else{
            return ResponseEntity.ok("Error of addition Transaction");
        }
    }


    //InformationCode sorguları
    @PostMapping("/information-codes")
    public ResponseEntity<String> addInformationCode(@RequestBody InformationCodeDto informationCodeDto) {
        if (informationCodeService.addInformationCode(informationCodeDto)) {
            return ResponseEntity.ok("InformationCode updated successfully");
        } else {
            return ResponseEntity.ok("Error updating InformationCode");
        }
    }

    //Add bank Account Info
    @PostMapping("/bankAccountInfos")
    public ResponseEntity<String> addBankAccountInfo (@RequestBody BankAccountInfoDto bankAccountInfo){
            if(bankAccountInfoService.addBankAccountInfo(bankAccountInfo)){
                return ResponseEntity.ok("Bank account infos succeed");
            } else {
                return ResponseEntity.ok("Bank account infos error");
            }
    }

    //Add balance
    @PostMapping("/balances")
    public ResponseEntity<String> addBalance(@RequestBody BalanceDto balance,@RequestParam("name") String name){
        if(balanceService.addBalance(balance,name)){

            return ResponseEntity.ok( "Balance succeed");
        } else {
            return ResponseEntity.ok("Balance error");
        }
    }

    @PostMapping("/balance/transfer")
    public ResponseEntity<String> balancetransfer(@RequestBody BalanceTransferDto balanceTransferDto) {
        String transferResult = balanceTransferService.transfer(balanceTransferDto);

        if (transferResult != null) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hata transfer");
        }

    }


    // Quantity ekleme
    @PatchMapping("/{stockId}/updateQuantityIn")
    public ResponseEntity<String> updateQuantityIn(@PathVariable Long stockId, @RequestParam Integer quantityIn) {

        if (warehouseStockService.updateQuantityIn(stockId, quantityIn)) {
            return ResponseEntity.status(HttpStatus.OK).body("WarehouseStock updateQuantityIn guncellendi:" + stockId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hata updateQuantityIn:" + stockId);
    }

    @PatchMapping("/{clientID}/updateBalance")
    public ResponseEntity<String> updateBalance(@PathVariable Long clientID,@RequestParam String paymentType, @RequestParam BigDecimal value){
        if(balanceService.updateBalance(clientID,paymentType,value)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Updated UpdateBalance");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error UpdateBalance");
    }

    // Quantity çıkarma
    @PatchMapping("/{stockId}/updateQuantityOut")
    public ResponseEntity<String> updateQuantityOut(@PathVariable Long stockId, @RequestParam Integer quantityOut) {

        if (warehouseStockService.updateQuantityOut(stockId, quantityOut)) {
            return ResponseEntity.status(HttpStatus.OK).body("WarehouseStock  updateQuantityOut guncellendi:" + stockId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hata updateQuantityOut:" + stockId);
    }

    // bütün stocklar alma
    @GetMapping("/getStocks")
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();

        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(stocks);
        }
    }

    //  stocklar alma warehouse transfer için yapılan sadece id ve isim
    @GetMapping("/getStocksById")
    public ResponseEntity<List<StockWarehouseDto>> getstockwithid(@RequestParam("warehouse_id") Long warehouse_id) {
        List<StockWarehouseDto> stocks = stockService.getStockWithId(warehouse_id);

        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(stocks);
        }
    }

    //stock update
    @PostMapping("/stockUpdate")
    public ResponseEntity<Boolean> addPurchase(@RequestBody StockUpdateDto stockUpdateDto) {
        boolean transferResult = stockService.stockUpdate(stockUpdateDto);

        if (transferResult==true) {
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

        if (transferResult==true) {
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

        if (transferResult==true) {
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
        System.out.println(deleteDto.getId());
        boolean transferResult = stockService.deleteStock(deleteDto);

        if (transferResult==true) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transferResult);
        }
    }

    @GetMapping("getBalanceWithClientID")
    public ResponseEntity<Balance>findBalanceByClientID(@RequestParam("ClientID") Long ClientID){
        Balance balances = balanceService.findBalanceByClientID(ClientID);
        if (balances==null){
            return ResponseEntity.noContent().build();
        }else {
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
    //  kullanıcıdan  satılanların listesi ödeme için
    @GetMapping("/getSalesInvoiceClient")
    public ResponseEntity<List<ExpenseInvoiceDto2>> getClientSalesInvoicewithid(@RequestParam("client_id") Long client_id) {
        List<ExpenseInvoiceDto2> expenses = expenseInvoiceService.getExpenseWithId(client_id);
        if (expenses.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(expenses);
        }
    }

    @GetMapping("getClientByName")
    public ResponseEntity<Client> findClientByName(@RequestParam("name") String name){
        Client clients = clientService.getClientWithName(name);
        if (clients==null){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(clients);
        }
    }

    // bütün warehouseları alma
    @GetMapping("/getWarehouseStock")
    public ResponseEntity<List<WarehouseStock>> getAllWarehouseStock() {
        List<WarehouseStock> stocks = warehouseStockService.getAllWarehouseStock();
        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(stocks);
        }
    }
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
    @GetMapping("/getClients")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();

        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(clients);
        }
    }
    //Transaction Alma
    @GetMapping("getTransactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> transactions = transactionService.getAllTransactions();
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(transactions);
        }
    }

    //Balance Alma
    @GetMapping("/getBalances")
    public ResponseEntity<List<Balance>> getAllBalances(){
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

    //purchase oluşturma
    @PostMapping("/purchase")
    public ResponseEntity<Boolean> addPurchase(@RequestBody PurchaseDto purchaseDto) {
        boolean transferResult = purchaseService.addPurchase(purchaseDto);

        if (transferResult==true) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transferResult);
        }
    }
    //sales satın alma faturası eklenecek
    @GetMapping("/getSales")
    public ResponseEntity<List<ExpenseInvoiceDto2>> getAllSales() {
        List<ExpenseInvoiceDto2> Sales = expenseInvoiceService.getAllExpenseInvoice();
        if (Sales.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(Sales);
        }
    }

    //sales oluşturma
    @PostMapping("/Sales")
    public ResponseEntity<Boolean> addPurchase(@RequestBody ExpenseInvoiceDto expenseInvoiceDto) {
        boolean transferResult = expenseInvoiceService.addExpenseInvoice(expenseInvoiceDto);

        if (transferResult==true) {
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

        if (Objects.equals(transferResult, "transfer basarılı")) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transferResult);
        }
    }

    // get waiting warehouse transfer

    @GetMapping("/warehouseStock/get_waiting_transfer")
    public ResponseEntity<List<Map<String, String>>>getwaitngtransfer(){
        List<WarehouseTransfer> warehouseTransfers = warehouseTransferService.getallwaitingtransfer();
        List<Map<String, String>> transferNames = new ArrayList<>();
        for (WarehouseTransfer transfer : warehouseTransfers) {
            Map<String, String> names = new HashMap<>();
            names.put("id", transfer.getWarehouseTransferId().toString());
            names.put("source", transfer.getSource().getName());
            names.put("target", transfer.getTarget().getName());
            names.put("quantity",transfer.getQuantity().toString());
            names.put("comment",transfer.getComment());
            names.put("approvelstatus",transfer.getApprovalStatus());
            transferNames.add(names);
        }
        return ResponseEntity.ok(transferNames);
    }

    // approvelStatus change
    @PatchMapping("/{warehouse_transfer_id}/approvelStatus")
    public ResponseEntity<String> change_approvelStatus(@PathVariable Long warehouse_transfer_id, @RequestParam String status) {
        if(Objects.equals(status, "onay")){
            warehouseTransferService.change_status(warehouse_transfer_id,status);
            return ResponseEntity.ok("onaylandı");
        }
        else if(Objects.equals(status, "red")){
            warehouseTransferService.change_status(warehouse_transfer_id,status);
            return ResponseEntity.ok("red edildi ve miktar kaynaga tekrardan aktarıldı");
        }
        else
            return ResponseEntity.badRequest().body("yanlış değer");

    }

    @GetMapping("/getDailyExpenses")
    public List<Object> getDailyExpenses(@RequestParam("date") LocalDate date) {
        return reportService.getDailyExpenses(date);
    }

}
