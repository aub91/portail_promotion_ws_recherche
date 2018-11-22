package fr.afcepf.al32.wsrecherche.dto;

import fr.afcepf.al32.wsrecherche.entity.Shop;
import lombok.Data;


@Data 
public class ShopDto {
	private Long id;

	public ShopDto(Shop shop) {
		this.id=shop.getId();
	}
}
