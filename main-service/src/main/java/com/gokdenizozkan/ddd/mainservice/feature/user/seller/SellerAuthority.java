package com.gokdenizozkan.ddd.mainservice.feature.user.seller;

public enum SellerAuthority {
    ROLE_MANAGER;

    public String getAuthority() {
        return this.name();
    }
}
