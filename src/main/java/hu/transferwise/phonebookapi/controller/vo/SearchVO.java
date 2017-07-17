package hu.transferwise.phonebookapi.controller.vo;


import lombok.Data;

@Data
public class SearchVO {

    private String query;
    private Integer page = 0;
    private Integer pageSize = 20;
}
