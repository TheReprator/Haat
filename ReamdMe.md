## 📐 Architecture Diagram

```
        ┌─────────────┐
        │ Composable  │
        │ (UI Layer)  │◄───── Collects ─────┐
        └────┬────────┘                     │
             │                              │
             ▼                              │
        ┌───────────────┐       Emits       │
        │  ViewModel    │─────────────────► SideEffect (e.g., NavigateToDetail)
        │  (MVI entry)  │                   │
        └────┬──────────┘                   │
             │ dispatches                   ▼
             ▼                         ┌─────────────┐
        ┌───────────────┐              │NavController│
        │   Delegate    │              └─────────────┘
        │ (MVI Engine)  │
        └────┬──────┬───┘
             │      │
             │      │
             ▼      ▼
        ┌────────┐ ┌────────────────────────────┐
        │Reducer │ │        Middleware          │
        │(Sync)  │ │(Async: API, Storage, etc.) │
        └────────┘ └────────────────────────────┘
```

1) I am using InMemory, for CTA
2) In HomePage:
     a) Only layouts structure has been achieved
3) In Store/Product Detail:
   a) Only layouts structure has been achieved
   b) Timing, delivery, offer is static
   c) ViewAll Footer in horizontal scroll is visibly only, if total item is greater than 4