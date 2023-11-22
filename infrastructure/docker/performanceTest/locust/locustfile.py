from locust import HttpUser, task, between
import json
import random

class ApiUser(HttpUser):
    wait_time = between(1, 2)

    def on_start(self):
        self.reservations = self.load_reservations()
        self.establishment_id = "00000000-0000-0000-0000-000000000000"
        self.room_type_id = self.get_room_type_id()
    
    def get_room_type_id(self):
        response = self.client.get(f"/api/establishments/{self.establishment_id}/rooms/types")     
        if response.status_code == 200:
            roomsType = json.loads(response.text)
            return [room["id"] for room in roomsType] 
        else:
            print(f"Error: {response.status_code}")


    def load_reservations(self):
        with open("/mnt/locust/reservation.json", "r") as file:
            return json.load(file)

    @task
    def create_reservation(self):
        reservation = random.choice(self.reservations)
        roomType = random.choice(self.room_type_id)
        quantity = random.randint(1, 5)
        reservation["roomTypes"][0]["roomTypeId"] = roomType
        reservation["roomTypes"][0]["quantity"] = quantity
        reservation["establishmentId"] = self.establishment_id
        self.client.post("/api/reservation", json=reservation)

    @task
    def get_room_rack(self):
        self.client.get(f"/api/establishments/{self.establishment_id}/rooms/")

