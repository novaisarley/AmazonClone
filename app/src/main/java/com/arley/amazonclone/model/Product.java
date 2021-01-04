package com.arley.amazonclone.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    String codigo;
    String imagem_url;
    String nome;
    String vendedor;
    String empresa;
    String descricao;
    float nota_avaliacao;
    float preco;
    int quantidade;
    int quantidade_avaliacao;

    public Product() {
    }

    public Product(String codigo, String imagem_url, String nome, String vendedor, String empresa, String descricao, float nota_avaliacao, float preco, int quantidade, int quantidade_avaliacao) {
        this.codigo = codigo;
        this.imagem_url = imagem_url;
        this.nome = nome;
        this.vendedor = vendedor;
        this.empresa = empresa;
        this.descricao = descricao;
        this.nota_avaliacao = nota_avaliacao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.quantidade_avaliacao = quantidade_avaliacao;
    }

    protected Product(Parcel in) {
        codigo = in.readString();
        imagem_url = in.readString();
        nome = in.readString();
        vendedor = in.readString();
        empresa = in.readString();
        descricao = in.readString();
        nota_avaliacao = in.readFloat();
        preco = in.readFloat();
        quantidade = in.readInt();
        quantidade_avaliacao = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codigo);
        dest.writeString(imagem_url);
        dest.writeString(nome);
        dest.writeString(vendedor);
        dest.writeString(empresa);
        dest.writeString(descricao);
        dest.writeFloat(nota_avaliacao);
        dest.writeFloat(preco);
        dest.writeInt(quantidade);
        dest.writeInt(quantidade_avaliacao);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
