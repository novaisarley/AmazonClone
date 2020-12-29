package com.arley.amazonclone.model;

public class Product {
    String codigo;
    String imagem_url;
    String nome;
    String vendedor;
    float nota_avaliacao;
    float preco;
    int quantidade;
    int quantidade_avaliacao;

    public Product() {
    }

    public Product(String codigo, String imagem_url, String nome, String vendedor, float nota_avaliacao, float preco, int quantidade, int quantidade_avaliacao) {
        this.codigo = codigo;
        this.imagem_url = imagem_url;
        this.nome = nome;
        this.vendedor = vendedor;
        this.nota_avaliacao = nota_avaliacao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.quantidade_avaliacao = quantidade_avaliacao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getImagem_url() {
        return imagem_url;
    }

    public void setImagem_url(String imagem_url) {
        this.imagem_url = imagem_url;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public float getNota_avaliacao() {
        return nota_avaliacao;
    }

    public void setNota_avaliacao(float nota_avaliacao) {
        this.nota_avaliacao = nota_avaliacao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidade_avaliacao() {
        return quantidade_avaliacao;
    }

    public void setQuantidade_avaliacao(int quantidade_avaliacao) {
        this.quantidade_avaliacao = quantidade_avaliacao;
    }
}
