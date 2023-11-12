package com.skuplate.server.review.form;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ReviewForm {

    @NotEmpty(message = "별점은 필수 항목입니다.")
    @Size(max = 200)
    private long starRate;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
}
