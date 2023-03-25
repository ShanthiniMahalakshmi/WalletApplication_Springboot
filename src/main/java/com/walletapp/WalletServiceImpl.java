package com.walletapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public WalletDto registerWallet(WalletDto wallet) throws WalletException {
        return this.walletRepository.createWallet(wallet);
    }

    @Override
    public WalletDto getWalletById(Integer walletId) throws WalletException {

        WalletDto wallet = this.walletRepository.getWalletById(walletId);
        if(wallet==null)
            throw new WalletException("Wallet id does not exists:"+walletId);
        return wallet;
    }

    @Override
    public WalletDto updateWallet(WalletDto wallet) throws WalletException {
        return this.walletRepository.updateWallet(wallet);
    }

    @Override
    public WalletDto deleteWalletById(Integer walletId) throws WalletException {
        return this.walletRepository.deleteWalletById(walletId);
    }


    @Override
    public Double addFundsToWalletById(Integer walletId, Double amount) throws WalletException {
        WalletDto wallet = this.walletRepository.getWalletById(walletId);
        if(wallet==null)
            throw new WalletException("Wallet does not exists to add funds, id:"+walletId);
        Double newBalance = wallet.getBalance()+amount;
        wallet.setBalance(newBalance);
        this.walletRepository.updateWallet(wallet);
        return newBalance;
    }

    @Override
    public Double withdrawFundsFromWalletById(Integer walletById, Double amount) throws WalletException {
        WalletDto wallet = this.walletRepository.getWalletById(walletById);
        if(wallet==null)
            throw new WalletException("Wallet does not exists to withdraw, try using valid account id");
        Double balance= wallet.getBalance();
        if(balance<amount)
            throw new WalletException("Insufficient balance, current balance:"+balance);
        balance-=amount;
        wallet.setBalance(balance);
        this.walletRepository.updateWallet(wallet);
        return balance;
    }

    @Override
    public Boolean fundTransfer(Integer fromWalletId, Integer toWalletId, Double amount) throws WalletException {

        WalletDto fromWallet = this.walletRepository.getWalletById(fromWalletId);
        if(fromWallet == null)
            throw new WalletException("From wallet does not exists, id:"+fromWalletId);
        WalletDto toWallet = this.walletRepository.getWalletById(toWalletId);
        if(toWallet== null)
            throw new WalletException("To wallet does not exists, id:"+toWalletId);
        Double fromBalance = fromWallet.getBalance();
        if(fromBalance<amount)
            throw new WalletException("Insufficient balance, current balance:"+fromBalance);

        fromWallet.setBalance(fromBalance-amount);

        Double toBalance = toWallet.getBalance();
        toWallet.setBalance(toBalance+amount);

        this.walletRepository.updateWallet(fromWallet);
        this.walletRepository.updateWallet(toWallet);
        return true;
    }

    @Override
    public Collection<WalletDto> getAllWallets() {
        return this.walletRepository.getAllWallets();
    }
}
