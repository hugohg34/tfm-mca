<template>
    <div class="wrapper">
        <span v-if="rooms.length === 0">Loading...  </span>
        <span v-if="errorDescription"> {{ errorDescription }}</span>
        <div class="room-block" v-for="(block, index) in roomBlocks" :key="index">
            <div class="room" v-for="room in block" :key="room.id">
                <div class="room-number">{{ room.roomNumber }}</div>
                <div class="reservation-holder">
                    <span class="status-icons">
                        <span  class="icon" v-if="room.occupied" title="Occupied">👤</span>
                        <span class="icon" v-if="!room.occupied && room.reservationHolder" title="Arrival">➡️</span>
                    </span>
                    <span v-if="room.reservationHolder">{{ room.reservationHolder || '..' }}</span>
                    <span v-if="!room.occupied && !room.reservationHolder">Libre</span>
                </div>
                <div class="status-icons">
                    <span v-if="room.occupied" class="icon occupied">🟥</span>
                    <span v-else class="icon unoccupied">🟢</span>
                    <span v-if="room.incidentActive" class="icon incident-active">⚠️</span>
                    <span v-if="room.clean" class="icon clean">✅</span>
                    <span v-else class="icon dirty">🧹</span>
                    <span v-if="room.supervised" class="icon supervised">✅</span>
                </div>
                <div class="room-actions">
                    <button class="menu-button" @click="toggleMenu(room.id)">⋮</button>
                    <div v-if="room.showMenu" class="menu-options">
                        <button @click="putAction(room.id, 'clean')">Limpia</button>
                        <button @click="putAction(room.id, 'dirty')">Sucia</button>
                        <button @click="putAction(room.id, 'supervised')">Supervisada</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
import axios from 'axios';

const baseApiUrl = '/api/v1';
const establishmentId = '00000000-0000-0000-0000-000000000001';
 
export default {
    name: 'EstablishmentRack',
    data() {
        return {
            rooms: [],
            errorDescription: null
        }
    },
    created() {
        this.fetchData();
        setInterval(this.fetchData, 10000);
    },
    methods: {
        async fetchData() {
            console.log(new Date().toLocaleTimeString() + ' Fetching data...');
            try {
                const response = await axios.get(`${baseApiUrl}/establishments/${establishmentId}/rooms`);
                this.rooms = response.data;
                this.errorDescription = null;
            } catch (error) {
                this.errorDescription = error;
            }
        },
        toggleMenu(roomId) {
            this.rooms = this.rooms.map(room => {
                if (room.id === roomId) {
                    room.showMenu = !room.showMenu;
                } else {
                    room.showMenu = false;
                }
                return room;
            });
        },
        async putAction(id, action) {
            let roomActions = ['clean', 'dirty', 'supervised'];
            let url;
            if (roomActions.includes(action)) {
                url = `${baseApiUrl}/establishments/${establishmentId}/room/${id}/${action}`;
            } else if (action === 'checkIn') {
                url = `${baseApiUrl}/reservation/${id}/checkin`;
            }
            try {
                await axios.put(url);
            } catch (error) {
                console.error(error);
                window.alert('Error al actualizar el estado de la habitación');
            }
        }
    },
    computed: {
        roomBlocks() {
            let blocks = [];
            for (let i = 0; i < this.rooms.length; i += 10) {
                blocks.push(this.rooms.slice(i, i + 10));
            }
            return blocks;
        }
    }
}


</script>

<style>
* {
    box-sizing: border-box;
}

.wrapper{
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
}

.room-list {
    display: flex;
    flex-wrap: wrap;
    padding: 10px;
    background-color: #f4f4f4;
}

.room-block {
    display: flex;
    flex-direction: column;
    margin-right: 20px;
}

.room {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
    border: 1px solid #ddd;
    padding: 15px;
    background-color: white;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    transition: transform 0.2s;
    max-width: 600px;
}

.room:hover {
    transform: scale(1.02);
}

.room-number {
    font-size: 1.2rem;
    font-weight: bold;
    margin-right: 15px;
}

.reservation-holder {
    margin-right: 15px;
    width: 220px;
    text-align: left;
}

.status-icons .icon {
    font-size: 1.5rem;
    margin-right: 10px;
}

.room-actions {
    margin-left: auto;
    position: relative;
}

.menu-button {
    background: none;
    border: none;
    cursor: pointer;
    font-size: 1.5rem;
}

.menu-options {
    position: absolute;
    right: 0;
    background-color: white;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    z-index: 1;
}

.menu-options button {
    display: block;
    padding: 10px;
    border: none;
    background: none;
    width: 100%;
    text-align: left;
    cursor: pointer;
    border-bottom: 1px solid #ddd;
}

.menu-options button:last-child {
    border-bottom: none;
}

.menu-options button:hover {
    background-color: #f0f0f0;
}

@media (max-width: 768px) {
    .room-list {
        flex-direction: column;
    }

    .room-block {
        flex-direction: row;
        flex-wrap: wrap;
        margin-right: 0;
        margin-bottom: 20px;
    }

    .room {
        flex-basis: calc(50% - 10px);
        margin-right: 10px;
    }
}

@media (max-width: 480px) {
    .room {
        flex-basis: 100%;
        margin-right: 0;
    }

    .menu-options button {
        font-size: 0.9rem;
    }
}
</style>

