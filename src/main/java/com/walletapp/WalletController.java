package com.walletapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin(value ="http://localhost:4200/")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @RequestMapping(method = RequestMethod.GET, value = "/" )
   // @GetMapping("/")
    public String greet(){
        return "Hello welcome to wallet app.";
    }

    @PostMapping("wallet")
    public WalletDto registerWallet(@RequestBody WalletDto wallet) throws WalletException {
        return this.walletService.registerWallet(wallet);
    }

    @GetMapping("wallet/{id}")
    public WalletDto getWalletById(@PathVariable("id") Integer walletId) throws WalletException {
        return this.walletService.getWalletById(walletId);
    }
    @PutMapping("wallet")
    public WalletDto updateWallet(@RequestBody WalletDto wallet) throws WalletException {
        return this.walletService.updateWallet(wallet);
    }
    @DeleteMapping("wallet/{id}")
    public WalletDto deleteWallet(@PathVariable("id") Integer walletId) throws WalletException {
     return this.walletService.deleteWalletById(walletId);
    }

    @PatchMapping("wallet/{id}/{amount}")
    public Double addFundsToWalletById(@PathVariable("id")Integer walletId,@PathVariable("amount") Double amount) throws WalletException {
        return this.walletService.addFundsToWalletById(walletId,amount);
    }
    @PatchMapping("wallet/{id}/fund/{amount}")
    public Double withdrawFundsfromWalletById(@PathVariable("id")Integer walletId,@PathVariable("amount") Double amount) throws WalletException {
        return this.walletService.withdrawFundsFromWalletById(walletId,amount);
    }

    @PostMapping("wallet/fund")
    public Boolean fundTransfer(@RequestBody WalletTransferDto walletTransferDto) throws WalletException {
        return this.walletService.fundTransfer(walletTransferDto.getFromId(),walletTransferDto.getToId(),walletTransferDto.getAmount());
    }
    @GetMapping("wallets")
    public Collection<WalletDto> getAllWallets(){
        return this.walletService.getAllWallets();
    }
}
