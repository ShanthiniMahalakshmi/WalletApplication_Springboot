package com.walletapp;

import java.util.Collection;
import java.util.List;

public interface WalletRepository {
    WalletDto createWallet(WalletDto newWallet);
    WalletDto getWalletById(Integer  walletId);
    WalletDto updateWallet(WalletDto wallet);
    WalletDto deleteWalletById(Integer walletId);
    Collection<WalletDto> getAllWallets();
}
