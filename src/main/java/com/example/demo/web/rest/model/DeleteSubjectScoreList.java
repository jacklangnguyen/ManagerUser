package com.example.demo.web.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
public class DeleteSubjectScoreList {

    @NotEmpty
    @Schema(description = " subject Id List deleted", example = "1,2,3", required = true)
    private List<String> ids = new ArrayList<>();
}
