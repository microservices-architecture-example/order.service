package store.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data @Builder @Accessors(fluent = true, chain = true)
public class Order {
  String id;
  String accountId;
  LocalDateTime date;
  List<ItemOut> items;
  Double total;
}