package cn.nukkit.network.protocol;

import cn.nukkit.api.DeprecationDetails;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import com.google.common.primitives.Ints;

import java.util.List;

import static cn.nukkit.utils.Utils.dynamic;

/**
 * @author MagicDroidX &amp; iNevet (Nukkit Project)
 */
public interface ProtocolInfo {

    /**
     * Actual Minecraft: PE protocol version
     */
    int CURRENT_PROTOCOL = dynamic(649);

    List<Integer> SUPPORTED_PROTOCOLS = Ints.asList(CURRENT_PROTOCOL);

    String MINECRAFT_VERSION_NETWORK = dynamic("1.20.60");

    String MINECRAFT_VERSION = 'v' + MINECRAFT_VERSION_NETWORK;

    byte LOGIN_PACKET = 0x01;
    byte PLAY_STATUS_PACKET = 0x02;
    byte SERVER_TO_CLIENT_HANDSHAKE_PACKET = 0x03;
    byte CLIENT_TO_SERVER_HANDSHAKE_PACKET = 0x04;
    byte DISCONNECT_PACKET = 0x05;
    byte RESOURCE_PACKS_INFO_PACKET = 0x06;
    byte RESOURCE_PACK_STACK_PACKET = 0x07;
    byte RESOURCE_PACK_CLIENT_RESPONSE_PACKET = 0x08;
    byte TEXT_PACKET = 0x09;
    byte SERVER_POST_MOVE_POSITION = 0x10;
    byte SET_TIME_PACKET = 0x0a;
    byte START_GAME_PACKET = 0x0b;
    byte ADD_PLAYER_PACKET = 0x0c;
    byte ADD_ENTITY_PACKET = 0x0d;
    byte REMOVE_ENTITY_PACKET = 0x0e;
    byte ADD_ITEM_ENTITY_PACKET = 0x0f;
    byte TAKE_ITEM_ENTITY_PACKET = 0x11;
    byte MOVE_ENTITY_ABSOLUTE_PACKET = 0x12;
    byte MOVE_PLAYER_PACKET = 0x13;
    byte RIDER_JUMP_PACKET = 0x14;
    byte UPDATE_BLOCK_PACKET = 0x15;
    byte ADD_PAINTING_PACKET = 0x16;
    byte TICK_SYNC_PACKET = 0x17;
    byte LEVEL_SOUND_EVENT_PACKET_V1 = 0x18;
    byte LEVEL_EVENT_PACKET = 0x19;
    byte BLOCK_EVENT_PACKET = 0x1a;
    byte ENTITY_EVENT_PACKET = 0x1b;
    byte MOB_EFFECT_PACKET = 0x1c;
    byte UPDATE_ATTRIBUTES_PACKET = 0x1d;
    byte INVENTORY_TRANSACTION_PACKET = 0x1e;
    byte MOB_EQUIPMENT_PACKET = 0x1f;
    byte MOB_ARMOR_EQUIPMENT_PACKET = 0x20;
    byte INTERACT_PACKET = 0x21;
    byte BLOCK_PICK_REQUEST_PACKET = 0x22;
    byte ENTITY_PICK_REQUEST_PACKET = 0x23;
    byte PLAYER_ACTION_PACKET = 0x24;
    byte ENTITY_FALL_PACKET = 0x25;
    byte HURT_ARMOR_PACKET = 0x26;
    byte SET_ENTITY_DATA_PACKET = 0x27;
    byte SET_ENTITY_MOTION_PACKET = 0x28;
    byte SET_ENTITY_LINK_PACKET = 0x29;
    byte SET_HEALTH_PACKET = 0x2a;
    byte SET_SPAWN_POSITION_PACKET = 0x2b;
    byte ANIMATE_PACKET = 0x2c;
    byte RESPAWN_PACKET = 0x2d;
    byte CONTAINER_OPEN_PACKET = 0x2e;
    byte CONTAINER_CLOSE_PACKET = 0x2f;
    byte PLAYER_HOTBAR_PACKET = 0x30;
    byte INVENTORY_CONTENT_PACKET = 0x31;
    byte INVENTORY_SLOT_PACKET = 0x32;
    byte CONTAINER_SET_DATA_PACKET = 0x33;
    byte CRAFTING_DATA_PACKET = 0x34;
    byte CRAFTING_EVENT_PACKET = 0x35;
    byte GUI_DATA_PICK_ITEM_PACKET = 0x36;
    @Deprecated
    byte ADVENTURE_SETTINGS_PACKET = 0x37;
    byte BLOCK_ENTITY_DATA_PACKET = 0x38;
    byte PLAYER_INPUT_PACKET = 0x39;
    byte FULL_CHUNK_DATA_PACKET = 0x3a;
    byte SET_COMMANDS_ENABLED_PACKET = 0x3b;
    byte SET_DIFFICULTY_PACKET = 0x3c;
    byte CHANGE_DIMENSION_PACKET = 0x3d;
    byte SET_PLAYER_GAME_TYPE_PACKET = 0x3e;
    byte PLAYER_LIST_PACKET = 0x3f;
    byte SIMPLE_EVENT_PACKET = 0x40;
    byte EVENT_PACKET = 0x41;
    byte SPAWN_EXPERIENCE_ORB_PACKET = 0x42;
    byte CLIENTBOUND_MAP_ITEM_DATA_PACKET = 0x43;
    byte MAP_INFO_REQUEST_PACKET = 0x44;
    byte REQUEST_CHUNK_RADIUS_PACKET = 0x45;
    byte CHUNK_RADIUS_UPDATED_PACKET = 0x46;
    byte ITEM_FRAME_DROP_ITEM_PACKET = 0x47;
    byte GAME_RULES_CHANGED_PACKET = 0x48;
    byte CAMERA_PACKET = 0x49;
    byte BOSS_EVENT_PACKET = 0x4a;
    byte SHOW_CREDITS_PACKET = 0x4b;
    byte AVAILABLE_COMMANDS_PACKET = 0x4c;
    byte COMMAND_REQUEST_PACKET = 0x4d;
    byte COMMAND_BLOCK_UPDATE_PACKET = 0x4e;
    byte COMMAND_OUTPUT_PACKET = 0x4f;
    byte UPDATE_TRADE_PACKET = 0x50;
    byte UPDATE_EQUIPMENT_PACKET = 0x51;
    byte RESOURCE_PACK_DATA_INFO_PACKET = 0x52;
    byte RESOURCE_PACK_CHUNK_DATA_PACKET = 0x53;
    byte RESOURCE_PACK_CHUNK_REQUEST_PACKET = 0x54;
    byte TRANSFER_PACKET = 0x55;
    byte PLAY_SOUND_PACKET = 0x56;
    byte STOP_SOUND_PACKET = 0x57;
    byte SET_TITLE_PACKET = 0x58;
    byte ADD_BEHAVIOR_TREE_PACKET = 0x59;
    byte STRUCTURE_BLOCK_UPDATE_PACKET = 0x5a;
    byte SHOW_STORE_OFFER_PACKET = 0x5b;
    byte PURCHASE_RECEIPT_PACKET = 0x5c;
    byte PLAYER_SKIN_PACKET = 0x5d;
    byte SUB_CLIENT_LOGIN_PACKET = 0x5e;
    byte INITIATE_WEB_SOCKET_CONNECTION_PACKET = 0x5f;
    byte SET_LAST_HURT_BY_PACKET = 0x60;
    byte BOOK_EDIT_PACKET = 0x61;
    byte NPC_REQUEST_PACKET = 0x62;
    byte PHOTO_TRANSFER_PACKET = 0x63;
    byte MODAL_FORM_REQUEST_PACKET = 0x64;
    byte MODAL_FORM_RESPONSE_PACKET = 0x65;
    byte SERVER_SETTINGS_REQUEST_PACKET = 0x66;
    byte SERVER_SETTINGS_RESPONSE_PACKET = 0x67;
    byte SHOW_PROFILE_PACKET = 0x68;
    byte SET_DEFAULT_GAME_TYPE_PACKET = 0x69;
    @PowerNukkitXOnly
    @Since("1.6.0.0-PNX")
    byte REMOVE_OBJECTIVE_PACKET = 0x6a;
    @PowerNukkitXOnly
    @Since("1.6.0.0-PNX")
    byte SET_DISPLAY_OBJECTIVE_PACKET = 0x6b;
    @PowerNukkitXOnly
    @Since("1.6.0.0-PNX")
    byte SET_SCORE_PACKET = 0x6c;
    @PowerNukkitXOnly
    @Since("1.19.30-r1")
    byte LAB_TABLE_PACKET = 0x6d;
    @PowerNukkitXOnly
    @Since("1.19.30-r1")
    byte UPDATE_BLOCK_SYNCED_PACKET = 0x6e;
    byte MOVE_ENTITY_DELTA_PACKET = 0x6f;
    byte SET_SCOREBOARD_IDENTITY_PACKET = 0x70;
    byte SET_LOCAL_PLAYER_AS_INITIALIZED_PACKET = 0x71;
    byte UPDATE_SOFT_ENUM_PACKET = 0x72;
    byte NETWORK_STACK_LATENCY_PACKET = 0x73;

    byte SCRIPT_CUSTOM_EVENT_PACKET = 0x75;
    byte SPAWN_PARTICLE_EFFECT_PACKET = 0x76;
    byte AVAILABLE_ENTITY_IDENTIFIERS_PACKET = 0x77;
    byte LEVEL_SOUND_EVENT_PACKET_V2 = 0x78;
    byte NETWORK_CHUNK_PUBLISHER_UPDATE_PACKET = 0x79;
    byte BIOME_DEFINITION_LIST_PACKET = 0x7a;
    byte LEVEL_SOUND_EVENT_PACKET = 0x7b;
    byte LEVEL_EVENT_GENERIC_PACKET = 0x7c;
    byte LECTERN_UPDATE_PACKET = 0x7d;
    byte VIDEO_STREAM_CONNECT_PACKET = 0x7e;
    //byte ADD_ENTITY_PACKET = 0x7f;
    //byte REMOVE_ENTITY_PACKET = 0x80;
    byte CLIENT_CACHE_STATUS_PACKET = (byte) 0x81;
    byte ON_SCREEN_TEXTURE_ANIMATION_PACKET = (byte) 0x82;
    byte MAP_CREATE_LOCKED_COPY_PACKET = (byte) 0x83;
    byte STRUCTURE_TEMPLATE_DATA_EXPORT_REQUEST = (byte) 0x84;
    byte STRUCTURE_TEMPLATE_DATA_EXPORT_RESPONSE = (byte) 0x85;
    byte UPDATE_BLOCK_PROPERTIES = (byte) 0x86;
    byte CLIENT_CACHE_BLOB_STATUS_PACKET = (byte) 0x87;
    byte CLIENT_CACHE_MISS_RESPONSE_PACKET = (byte) 0x88;
    byte EDUCATION_SETTINGS_PACKET = (byte) 0x89;
    byte EMOTE_PACKET = (byte) 0x8a;
    byte MULTIPLAYER_SETTINGS_PACKET = (byte) 0x8b;
    byte SETTINGS_COMMAND_PACKET = (byte) 0x8c;
    byte ANVIL_DAMAGE_PACKET = (byte) 0x8d;
    byte COMPLETED_USING_ITEM_PACKET = (byte) 0x8e;
    byte NETWORK_SETTINGS_PACKET = (byte) 0x8f;
    byte PLAYER_AUTH_INPUT_PACKET = (byte) 0x90;
    @Since("1.3.0.0-PN")
    byte CREATIVE_CONTENT_PACKET = (byte) 0x91;
    @Since("1.3.0.0-PN")
    byte PLAYER_ENCHANT_OPTIONS_PACKET = (byte) 0x92;
    @Since("1.3.0.0-PN")
    byte ITEM_STACK_REQUEST_PACKET = (byte) 0x93;
    @Since("1.3.0.0-PN")
    byte ITEM_STACK_RESPONSE_PACKET = (byte) 0x94;
    @Since("1.3.0.0-PN")
    byte PLAYER_ARMOR_DAMAGE_PACKET = (byte) 0x95;
    @Since("1.3.0.0-PN")
    byte CODE_BUILDER_PACKET = (byte) 0x96;
    @Since("1.3.0.0-PN")
    byte UPDATE_PLAYER_GAME_TYPE_PACKET = (byte) 0x97;
    @Since("1.3.0.0-PN")
    byte EMOTE_LIST_PACKET = (byte) 0x98;
    @Since("1.3.0.0-PN")
    byte POS_TRACKING_SERVER_BROADCAST_PACKET = (byte) 0x99;
    @Since("1.3.0.0-PN")
    byte POS_TRACKING_CLIENT_REQUEST_PACKET = (byte) 0x9a;
    @Since("1.3.0.0-PN")
    byte DEBUG_INFO_PACKET = (byte) 0x9b;
    @Since("1.3.0.0-PN")
    byte PACKET_VIOLATION_WARNING_PACKET = (byte) 0x9c;
    @Since("1.4.0.0-PN")
    byte MOTION_PREDICTION_HINTS_PACKET = (byte) 0x9d;
    @Since("1.4.0.0-PN")
    byte ANIMATE_ENTITY_PACKET = (byte) 0x9e;
    @Since("1.4.0.0-PN")
    byte CAMERA_SHAKE_PACKET = (byte) 0x9f;
    @Since("1.4.0.0-PN")
    byte PLAYER_FOG_PACKET = (byte) 0xa0;
    @Since("1.4.0.0-PN")
    byte CORRECT_PLAYER_MOVE_PREDICTION_PACKET = (byte) 0xa1;
    @Since("1.4.0.0-PN")
    byte ITEM_COMPONENT_PACKET = (byte) 0xa2;
    @Since("1.4.0.0-PN")
    byte FILTER_TEXT_PACKET = (byte) 0xa3;
    @Since("1.4.0.0-PN")
    byte CLIENTBOUND_DEBUG_RENDERER_PACKET = (byte) 0xa4;
    @Since("1.5.2.0-PN")
    @PowerNukkitOnly
    byte SYNC_ENTITY_PROPERTY_PACKET = (byte) 0xa5;
    @Since("1.5.2.0-PN")
    @PowerNukkitOnly
    byte ADD_VOLUME_ENTITY_PACKET = (byte) 0xa6;
    @Since("1.5.2.0-PN")
    @PowerNukkitOnly
    byte REMOVE_VOLUME_ENTITY_PACKET = (byte) 0xa7;
    @Since("1.5.0.0-PN")
    @PowerNukkitOnly
    @Deprecated
    @DeprecationDetails(since = "1.5.2.0-PN", reason = "Incorrect naming convention", by = "PowerNukkit", replaceWith = "SYNC_ENTITY_PROPERTY_PACKET")
    byte SYNC_ENTITY_PROPERTY = SYNC_ENTITY_PROPERTY_PACKET;
    @Since("1.5.0.0-PN")
    @PowerNukkitOnly
    @Deprecated
    @DeprecationDetails(since = "1.5.2.0-PN", reason = "Incorrect naming convention", by = "PowerNukkit", replaceWith = "ADD_VOLUME_ENTITY_PACKET")
    byte ADD_VOLUME_ENTITY = ADD_VOLUME_ENTITY_PACKET;
    @Since("1.5.0.0-PN")
    @PowerNukkitOnly
    @Deprecated
    @DeprecationDetails(since = "1.5.2.0-PN", reason = "Incorrect naming convention", by = "PowerNukkit", replaceWith = "REMOVE_VOLUME_ENTITY_PACKET")
    byte REMOVE_VOLUME_ENTITY = REMOVE_VOLUME_ENTITY_PACKET;
    @Since("1.5.2.0-PN")
    @PowerNukkitOnly
    byte SIMULATION_TYPE_PACKET = (byte) 0xa8;
    @Since("1.5.2.0-PN")
    @PowerNukkitOnly
    byte NPC_DIALOGUE_PACKET = (byte) 0xa9;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte EDU_URI_RESOURCE_PACKET = (byte) 0xaa;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte CREATE_PHOTO_PACKET = (byte) 0xab;
    @Since("1.6.0.0-PNX")
    @PowerNukkitXOnly
    byte UPDATE_SUB_CHUNK_BLOCKS_PACKET = (byte) 0xac;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte PHOTO_INFO_REQUEST_PACKET = (byte) 0xad;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte SUB_CHUNK_PACKET = (byte) 0xae;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte SUB_CHUNK_REQUEST_PACKET = (byte) 0xaf;
    @Since("1.6.0.0-PNX")
    @PowerNukkitXOnly
    byte PLAYER_START_ITEM_COOL_DOWN_PACKET = (byte) 0xb0;
    @Since("1.6.0.0-PNX")
    @PowerNukkitXOnly
    byte SCRIPT_MESSAGE_PACKET = (byte) 0xb1;
    @Since("1.6.0.0-PNX")
    @PowerNukkitXOnly
    byte CODE_BUILDER_SOURCE_PACKET = (byte) 0xb2;
    @Since("1.6.0.0-PNX")
    @PowerNukkitXOnly
    byte AGENT_ACTION_EVENT_PACKET = (byte) 0xb3;
    @Since("1.6.0.0-PNX")
    @PowerNukkitXOnly
    byte CHANGE_MOB_PROPERTY_PACKET = (byte) 0xb4;
    @Since("1.6.0.0-PNX")
    @PowerNukkitXOnly
    byte DIMENSION_DATA_PACKET = (byte) 0xb5;
    @Since("1.6.0.0-PNX")
    @PowerNukkitXOnly
    byte TICKING_AREAS_LOAD_STATUS_PACKET = (byte) 0xb6;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte LESSON_PROGRESS_PACKET = (byte) 0xb7;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte REQUEST_ABILITY_PACKET = (byte) 0xb8;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte REQUEST_PERMISSIONS_PACKET = (byte) 0xb9;
    byte TOAST_REQUEST_PACKET = (byte) 0xba;
    @Since("1.6.0.0-PNX")
    @PowerNukkitXOnly
    byte UPDATE_ABILITIES_PACKET = (byte) 0xbb;
    @Since("1.6.0.0-PNX")
    @PowerNukkitXOnly
    byte UPDATE_ADVENTURE_SETTINGS_PACKET = (byte) 0xbc;
    @Since("1.6.0.0-PNX")
    @PowerNukkitXOnly
    byte DEATH_INFO_PACKET = (byte) 0xbd;
    @Since("1.6.0.0-PNX")
    @PowerNukkitXOnly
    byte EDITOR_NETWORK_PACKET = (byte) 0xbe;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte FEATURE_REGISTRY_PACKET = (byte) 0xbf;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte SERVER_STATS_PACKET = (byte) 0xc0;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte REQUEST_NETWORK_SETTINGS_PACKET = (byte) 0xc1;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte GAME_TEST_REQUEST_PACKET = (byte) 0xc2;
    @Since("1.19.30-r1")
    @PowerNukkitXOnly
    byte GAME_TEST_RESULTS_PACKET = (byte) 0xc3;
    byte UPDATE_CLIENT_INPUT_LOCKS = (byte) 0xc4;
    @Since("1.19.60-r1")
    @PowerNukkitXOnly
    byte CLIENT_CHEAT_ABILITY_PACKET = (byte) 0xc5;
    @Since("1.19.70-r1")
    @PowerNukkitXOnly
    byte CAMERA_PRESETS_PACKET = (byte) 0xc6;
    @Since("1.19.70-r1")
    @PowerNukkitXOnly
    byte UNLOCKED_RECIPES_PACKET = (byte) 0xc7;
    @Since("1.19.70-r1")
    @PowerNukkitXOnly
    int CAMERA_INSTRUCTION_PACKET = 300;
    @Since("1.19.80-r1")
    @PowerNukkitXOnly
    int COMPRESSED_BIOME_DEFINITIONS_LIST = 301;
    @Since("1.19.80-r1")
    @PowerNukkitXOnly
    int TRIM_DATA = 302;
    @Since("1.19.80-r1")
    @PowerNukkitXOnly
    int OPEN_SIGN = 303;
    @Since("1.20.10-r1")
    @PowerNukkitXOnly
    int AGENT_ANIMATION = 304;
    @Since("1.20.50-r1")
    @PowerNukkitXOnly
    int REFRESH_ENTITLEMENTS = 305;
    @Since("1.20.50-r1")
    @PowerNukkitXOnly
    int TOGGLE_CRAFTER_SLOT_REQUEST = 306;
    byte BATCH_PACKET = (byte) 0xff;

    static int toNewProtocolID(byte oldProtocolID) {
        return oldProtocolID & 0xff;
    }
}
