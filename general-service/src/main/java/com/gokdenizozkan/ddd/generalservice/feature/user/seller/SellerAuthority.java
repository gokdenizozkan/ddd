package com.gokdenizozkan.ddd.generalservice.feature.user.seller;

public enum SellerAuthority {
    ROLE_MANAGER;

    public String getAuthority() {
        return this.name();
    }
}
