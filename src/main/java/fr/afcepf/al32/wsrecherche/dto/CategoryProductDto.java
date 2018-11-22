package fr.afcepf.al32.wsrecherche.dto;

import fr.afcepf.al32.wsrecherche.entity.CategoryProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CategoryProductDto {
	private Long id;

	public CategoryProductDto(CategoryProduct categoryProduct) {
		this.id=categoryProduct.getId();
	}
}
