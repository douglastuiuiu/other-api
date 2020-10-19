package br.com.douglasog87.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Entity
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String kind;
    @NotEmpty
    private String manufacturer;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", kind='" + kind + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }
}
