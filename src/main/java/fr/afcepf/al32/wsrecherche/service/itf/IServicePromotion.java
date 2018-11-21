package fr.afcepf.al32.wsrecherche.service.itf;

import java.util.List;

import fr.afcepf.al32.wsrecherche.entity.BaseProduct;
import fr.afcepf.al32.wsrecherche.entity.Promotion;
import fr.afcepf.al32.wsrecherche.entity.Shop;

public interface IServicePromotion {
	List<Promotion> findAll();
	List<Promotion> findAllValid();
	Promotion recherchePromotionParIdentifiant(Long idUnite);
	Promotion ajouterPromotion(Promotion promotion);
	List<Promotion> getAllValidPromotionByProduct(List<BaseProduct> products);
	List<Promotion> getAllValidPromotionByShop(List<Shop> shops);
}
