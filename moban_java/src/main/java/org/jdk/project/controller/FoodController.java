package org.jdk.project.controller;

import lombok.RequiredArgsConstructor;
import org.jdk.project.dto.ListResponse;
import org.jdk.project.dto.food.FoodDailyUpsertRequest;
import org.jdk.project.dto.food.FoodItemUpsertRequest;
import org.jdk.project.dto.food.FoodOrderUpsertRequest;
import org.jdk.project.dto.food.FoodPlanUpsertRequest;
import org.jooq.generated.project.tables.pojos.FoodDailyMenu;
import org.jooq.generated.project.tables.pojos.FoodItem;
import org.jooq.generated.project.tables.pojos.FoodOrder;
import org.jooq.generated.project.tables.pojos.FoodPlan;
import org.jdk.project.service.FoodService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

  private final FoodService foodService;

  @GetMapping("/items")
  public ListResponse<FoodItem> listItems() {
    return foodService.listFoodItems();
  }

  @PostMapping("/items")
  public Long createItem(@RequestBody FoodItemUpsertRequest request) {
    return foodService.createFoodItem(request);
  }

  @PutMapping("/items/{id}")
  public void updateItem(@PathVariable Long id, @RequestBody FoodItemUpsertRequest request) {
    foodService.updateFoodItem(id, request);
  }

  @DeleteMapping("/items/{id}")
  public void deleteItem(@PathVariable Long id) {
    foodService.deleteFoodItem(id);
  }

  @GetMapping("/daily-menus")
  public ListResponse<FoodDailyMenu> listDailyMenus() {
    return foodService.listFoodDailies();
  }

  @PostMapping("/daily-menus")
  public Long createDailyMenu(@RequestBody FoodDailyUpsertRequest request) {
    return foodService.createFoodDaily(request);
  }

  @PutMapping("/daily-menus/{id}")
  public void updateDailyMenu(@PathVariable Long id, @RequestBody FoodDailyUpsertRequest request) {
    foodService.updateFoodDaily(id, request);
  }

  @DeleteMapping("/daily-menus/{id}")
  public void deleteDailyMenu(@PathVariable Long id) {
    foodService.deleteFoodDaily(id);
  }

  @GetMapping("/plans")
  public ListResponse<FoodPlan> listPlans() {
    return foodService.listFoodPlans();
  }

  @PostMapping("/plans")
  public Long createPlan(@RequestBody FoodPlanUpsertRequest request) {
    return foodService.createFoodPlan(request);
  }

  @PutMapping("/plans/{id}")
  public void updatePlan(@PathVariable Long id, @RequestBody FoodPlanUpsertRequest request) {
    foodService.updateFoodPlan(id, request);
  }

  @DeleteMapping("/plans/{id}")
  public void deletePlan(@PathVariable Long id) {
    foodService.deleteFoodPlan(id);
  }

  @GetMapping("/orders")
  public ListResponse<FoodOrder> listOrders() {
    return foodService.listFoodOrders();
  }

  @PostMapping("/orders")
  public Long createOrder(@RequestBody FoodOrderUpsertRequest request) {
    return foodService.createFoodOrder(request);
  }

  @PutMapping("/orders/{id}")
  public void updateOrder(@PathVariable Long id, @RequestBody FoodOrderUpsertRequest request) {
    foodService.updateFoodOrder(id, request);
  }

  @DeleteMapping("/orders/{id}")
  public void deleteOrder(@PathVariable Long id) {
    foodService.deleteFoodOrder(id);
  }

  @PostMapping("/orders/{id}/receive")
  public void receiveOrder(@PathVariable Long id) {
    foodService.receiveOrder(id);
  }

  @PostMapping("/orders/{id}/finish")
  public void finishOrder(@PathVariable Long id) {
    foodService.finishOrder(id);
  }

  @PostMapping("/orders/{id}/cancel")
  public void cancelOrder(@PathVariable Long id) {
    foodService.cancelOrder(id);
  }
}
