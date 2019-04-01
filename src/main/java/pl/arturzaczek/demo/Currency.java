package pl.arturzaczek.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class Currency implements Serializable {
    private String currency;
    private String code;
    private Float mid;
}
