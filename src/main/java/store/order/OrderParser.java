package store.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import store.product.ProductOut;

public class OrderParser {

  public static OrderOut toOut(OrderModel om, List<ProductOut> products) {
    if (om == null) return null;

    Map<String, ProductOut> productMap =
        products.stream().collect(Collectors.toMap(ProductOut::id, Function.identity(), (p1, p2) -> p1));

    var itemsOut =
        om.getItems().stream()
            .map(
                im -> {
                  ProductOut product = productMap.get(im.getProductId());

                  return ItemOut.builder()
                      .id(im.getId())
                      .product(product)
                      .quantity(im.getQuantity())
                      .total(im.getTotal())
                      .build();
                })
            .toList();

    return OrderOut.builder()
        .id(om.getId())
        .date(om.getDate().toString())
        .items(itemsOut)
        .total(om.getTotal())
        .build();
  }

  public static OrderModel toModel(OrderIn in, String idAccount) {
    OrderModel om = new OrderModel();
    om.setId(null);
    om.setDate(LocalDateTime.now());
    om.setAccountId(idAccount);
    om.setTotal(0.0);
    return om;
  }
}
