package com.example.demo.web.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;


public class DeleteUserList {

    @NotEmpty
    @Schema(description = "Resuse Reason Id List", example = "1,2,3", required = true)
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
