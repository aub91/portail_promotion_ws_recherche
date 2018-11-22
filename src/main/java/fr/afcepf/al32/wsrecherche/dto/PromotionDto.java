package fr.afcepf.al32.wsrecherche.dto;

import fr.afcepf.al32.wsrecherche.entity.Promotion;
import lombok.Data;


@Data 
public class PromotionDto {
	private Long id;

	public PromotionDto(Promotion promotion) {
		this.id=promotion.getId();
	}
}
