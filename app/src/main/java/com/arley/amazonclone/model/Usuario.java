package com.arley.amazonclone.model;

import java.util.List;

public class Usuario {
    String fullname;
    String email;
    String endereco;
    String uid;
    List<Product> carrinho;
    List<Product> meus_pedidos;

    public Usuario() {
    }

    public Usuario(String fullname, String email, String endereco, String uid, List<Product> carrinho, List<Product> meus_pedidos) {
        this.fullname = fullname;
        this.email = email;
        this.endereco = endereco;
        this.uid = uid;
        this.carrinho = carrinho;
        this.meus_pedidos = meus_pedidos;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Product> getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(List<Product> carrinho) {
        this.carrinho = carrinho;
    }

    public List<Product> getMeus_pedidos() {
        return meus_pedidos;
    }

    public void setMeus_pedidos(List<Product> meus_pedidos) {
        this.meus_pedidos = meus_pedidos;
    }
}
