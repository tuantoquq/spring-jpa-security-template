package com.openway.springtemplateapollo.adapter;

public interface EntityAdapter <T, B>{
    public abstract B toMapper(T entity);
}
