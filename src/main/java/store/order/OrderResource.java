package store.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class OrderResource implements OrderController {

  @Autowired
  private OrderService orderService;

  @Override
  public ResponseEntity<OrderOut> create(String accountId, OrderIn in) {
    OrderOut out = orderService.create(in, accountId);
    return ResponseEntity.created(
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(out.id())
            .toUri()
    ).body(out);
  }

  @Override
  public ResponseEntity<List<OrderOut>> findAll(String accountId) {
    return ResponseEntity.ok(orderService.findAll(accountId));
  }

  @Override
  public ResponseEntity<OrderOut> findById(String accountId, String id) {
    return ResponseEntity.ok(orderService.findById(id, accountId));
  }
}