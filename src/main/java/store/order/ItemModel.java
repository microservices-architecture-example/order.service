package store.order;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item", schema = "\"order\"")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ItemModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private OrderModel order;

  @Column(name = "product_id", nullable = false)
  private String productId;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Column(name = "total", nullable = false)
  private Double total;
}