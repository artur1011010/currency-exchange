package pl.arturzaczek.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
public class Table implements Serializable {
    private String table;
    private String no;
    private String effectiveDate;
    private Currency [] rates;
}
