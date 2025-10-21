package store.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import store.product.ProductController;
import store.product.ProductOut;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductController productController;

  @Transactional
  public OrderOut create(OrderIn in, String idAccount) {
    if (in == null || in.items() == null || in.items().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Items are mandatory");
    }

    List<ProductOut> products = new ArrayList<>();
    in.items().forEach(it -> {
      ProductOut p = productController.findById(it.productId()).getBody();
      if (p == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found: " + it.productId());
      }
      products.add(p);
    });

    OrderModel om = OrderParser.toModel(in, idAccount);

    double orderTotal = 0.0;
    for (int i = 0; i < in.items().size(); i++) {
      var inItem = in.items().get(i);
      var product = products.get(i);

      double total = product.price() * inItem.quantity();

      ItemModel im = new ItemModel();
      im.setOrder(om);
      im.setProductId(inItem.productId());
      im.setQuantity(inItem.quantity());
      im.setTotal(total);

      om.getItems().add(im);
      orderTotal += total;
    }
    om.setTotal(orderTotal);

    OrderModel saved = orderRepository.save(om);
    return OrderParser.toOut(saved, products);
  }

  public List<OrderOut> findAll(String idAccount) {
    var list = orderRepository.findAllByAccountId(idAccount);
    return list.stream().map(om -> {
      List<ProductOut> products = om.getItems().stream()
          .map(it -> productController.findById(it.getProductId()).getBody())
          .toList();
      return OrderParser.toOut(om, products);
    }).toList();
  }

  public OrderOut findById(String id, String idAccount) {
    OrderModel om = orderRepository.findByIdAndAccountId(id, idAccount)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

    // carrega os produtos dos itens desse pedido
    List<ProductOut> products = om.getItems().stream()
        .map(it -> productController.findById(it.getProductId()).getBody())
        .toList();

    return OrderParser.toOut(om, products);
  }
}