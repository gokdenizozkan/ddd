package com.gokdenizozkan.ddd.user.seller;

public enum SellerAuthority {
    ROLE_MANAGER;

    public String getAuthority() {
        return this.name();
    }
}
