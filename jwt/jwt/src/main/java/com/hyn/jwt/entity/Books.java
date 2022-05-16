package com.hyn.jwt.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyn
 * @since 2021-10-21
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class Books implements Serializable {

    private static final long serialVersionUID=1L;

      private Integer id;

    private String name;

    private String price;


}
