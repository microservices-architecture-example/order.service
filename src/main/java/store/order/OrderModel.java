package store.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders", schema = "\"order\"")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "date", nullable = false)
  private LocalDateTime  date;

  @Column(name = "account_id", nullable = false)
  private String accountId;

  @Column(name = "total", nullable = false)
  private Double total;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<ItemModel> items = new ArrayList<>();
}