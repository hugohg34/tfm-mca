import express from "express";
import { handelGet, handlePost, handelPut } from "./roomRackProxyController.js";
import { getRoomsRackByEstablishment } from "./roomRackController.js";

const router = express.Router();

router.get('/establishments/:establishmentId/rooms', getRoomsRackByEstablishment);
router.get('/api/v1/*', handelGet);
router.post('/*', handlePost);
router.put('/*', handelPut);

export default router;
