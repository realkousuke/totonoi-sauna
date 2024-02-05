package com.example.portfoliosauna.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HouseEditForm {
	@NotNull
    private Integer id;
	
	 @NotBlank(message = "施設名を入力してください。")
     private String name;
         
     private MultipartFile imageFile;
     
     @NotBlank(message = "住所を入力してください。")
     private String address;
     
     @NotBlank(message = "アクセスを入力してください。")
     private String access ;
     
     @NotBlank(message = "電話番号を入力してください。")
     private String phoneNumber;
     
     @NotBlank(message = "定休日を入力してください。")
     private String holiday;
     
     @NotBlank(message = "営業日を入力してください。")
     private String opening_hours;
     
     @NotNull(message = "料金を入力してください。")
     @Min(value = 1, message = "料金は1円以上に設定してください。")
     private Integer price;
     
     @NotBlank(message = "説明を入力してください。")
     private String description;

}
