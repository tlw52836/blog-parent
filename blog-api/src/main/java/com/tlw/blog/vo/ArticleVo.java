package com.tlw.blog.vo;

import lombok.Data;
import java.util.List;

@Data
public class ArticleVo {
    //防止前端 精度损失 把id转为string
    // 分布式id 比较长，传到前端 会有精度损失，必须转为string类型 进行传输，就不会有问题了
    //@JsonSerialize(using = ToStringSerializer.class)
    //private Long id;
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;

}
