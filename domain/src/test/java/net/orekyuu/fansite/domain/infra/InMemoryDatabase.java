package net.orekyuu.fansite.domain.infra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class InMemoryDatabase<ID, DOMAIN> {
  private final Map<ID, DOMAIN> records = new HashMap<>();
  private final Function<DOMAIN, ID> idExtractor;

  protected InMemoryDatabase(Function<DOMAIN, ID> idExtractor) {
    this.idExtractor = idExtractor;
  }

  Optional<DOMAIN> findById(ID id) {
    return findByIds(List.of(id)).stream().findAny();
  }

  List<DOMAIN> findByIds(Iterable<ID> ids) {
    return StreamSupport.stream(ids.spliterator(), false)
            .map(records::get)
            .filter(Objects::nonNull)
            .toList();
  }

  List<DOMAIN> findAll() {
    return records.values().stream().toList();
  }

  List<DOMAIN> find(Predicate<DOMAIN> predicate) {
    return findAll().stream().filter(predicate).toList();
  }

  void insert(DOMAIN domain) {
    var id = idExtractor.apply(domain);
    if (records.containsKey(id)) {
      throw new UnsupportedOperationException("Duplicate id: " + id);
    }
    records.put(id, domain);
  }

  void upsert(DOMAIN domain) {
    var id = idExtractor.apply(domain);
    if (records.containsKey(id)) {
      update(domain);
    } else {
      insert(domain);
    }
  }

  void update(DOMAIN domain) {
    var id = idExtractor.apply(domain);
    if (!records.containsKey(id)) {
      throw new UnsupportedOperationException("Not found: " + id);
    }
    records.put(id, domain);
  }

  void delete(Iterable<ID> id) {
    id.forEach(records::remove);
  }

  void delete(ID id) {
    delete(List.of(id));
  }

  void deleteAll() {
    records.clear();
  }
}
