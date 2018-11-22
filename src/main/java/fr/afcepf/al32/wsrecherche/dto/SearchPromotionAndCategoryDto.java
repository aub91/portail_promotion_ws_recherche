package fr.afcepf.al32.wsrecherche.dto;

import java.util.List;

import fr.afcepf.al32.wsrecherche.entity.CategoryProduct;
import fr.afcepf.al32.wsrecherche.entity.Promotion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class SearchPromotionAndCategoryDto {

	private List<Promotion> promotions ;

	private CategoryProductDto categoryProductDto;

	private List<String> keyWords ;
	

}
