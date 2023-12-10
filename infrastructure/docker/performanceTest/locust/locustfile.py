import json
import random
from locust import HttpUser, task, between, events


class ApiUser(HttpUser):
    wait_time = between(1, 5)
    num_establishments = 200

    @task
    def get_room_rackTask(self):
        random_establishment_id = self.get_random_establishment_id()
        self.client.get("/api/v1/establishments/%s/rooms" %
                        random_establishment_id, name="/api/v1/establishments/[id]/rooms")

    @task
    def put_room_supervised(self):
        random_establishment_id = self.get_random_establishment_id()
        response = self.client.get(
            f"/api/v1/establishments/{random_establishment_id}/rooms", name="/api/v1/establishments/[id]/rooms")
        if response.status_code == 200:
            rooms = json.loads(response.text)
            superv_rooms = [room for room in rooms if not room["supervised"]]
            random_room = random.choice(superv_rooms) if superv_rooms else None
            if random_room:
                self.client.put("/api/v1/establishments/%s/room/%s/supervised"
                                % (random_establishment_id, random_room['id']),
                                json=random_room, name="/api/v1/establishments/[id]/room/[id]/supervised")

    @task(3)
    def put_room_clean(self):
        random_establishment_id = self.get_random_establishment_id()
        response = self.client.get(
            f"/api/v1/establishments/{random_establishment_id}/rooms", name="/api/v1/establishments/[id]/rooms")
        if response.status_code == 200:
            rooms = json.loads(response.text)
            superv_rooms = [room for room in rooms if not room["clean"]]
            random_room = random.choice(superv_rooms) if superv_rooms else None
            if random_room:
                resp = self.client.put("/api/v1/establishments/%s/room/%s/clean"
                                       % (random_establishment_id, random_room['id']),
                                       json=random_room, name="/api/v1/establishments/[id]/room/[id]/clean")

    @task(3)
    def put_room_dirty(self):
        random_establishment_id = self.get_random_establishment_id()
        response = self.client.get(
            f"/api/v1/establishments/{random_establishment_id}/rooms", name="/api/v1/establishments/[id]/rooms")
        if response.status_code == 200:
            rooms = json.loads(response.text)
            superv_rooms = [room for room in rooms if room["clean"]]
            random_room = random.choice(superv_rooms) if superv_rooms else None
            if random_room:
                resp = self.client.put("/api/v1/establishments/%s/room/%s/dirty"
                                       % (random_establishment_id,
                                          random_room['id']),
                                       json=random_room, name="/api/v1/establishments/[id]/room/[id]/dirty")

    def get_random_establishment_id(self):
        i = random.randint(1, self.num_establishments)
        return f"00000000-0000-0000-0000-{i:012}"
#
