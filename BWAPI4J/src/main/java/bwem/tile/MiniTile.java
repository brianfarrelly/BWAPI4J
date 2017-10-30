package bwem.tile;

import bwem.Altitude;
import bwem.area.AreaId;

public final class MiniTile {

    private static final AreaId blockingCP = new AreaId(Integer.MIN_VALUE);

    private Altitude m_altitude = new Altitude(-1); // 0 for seas  ;  != 0 for terrain and lakes (-1 = not computed yet)  ;  1 = SeaOrLake intermediate value
    private AreaId m_areaId = new AreaId(-1); // 0 -> unwalkable  ;  > 0 -> index of some Area  ;  < 0 -> some walkable terrain, but too small to be part of an Area

    public MiniTile() {
        /* Do nothing. */
    }

	// Corresponds approximatively to BWAPI::isWalkable
	// The differences are:
	//  - For each BWAPI's unwalkable MiniTile, we also mark its 8 neighbours as not walkable.
	//    According to some tests, this prevents from wrongly pretending one small unit can go by some thin path.
	//  - The relation buildable ==> walkable is enforced, by marking as walkable any MiniTile part of a buildable Tile (Cf. Tile::Buildable)
	// Among the MiniTiles having Altitude() > 0, the walkable ones are considered Terrain-MiniTiles, and the other ones Lake-MiniTiles.
    public boolean Walkable() {
        return (m_areaId.intValue() != 0);
    }

	// Distance in pixels between the center of this MiniTile and the center of the nearest Sea-MiniTile
	// Sea-MiniTiles all have their Altitude() equal to 0.
	// MiniTiles having Altitude() > 0 are not Sea-MiniTiles. They can be either Terrain-MiniTiles or Lake-MiniTiles.
    public Altitude Altitude() {
        return m_altitude;
    }

    // Sea-MiniTiles are unwalkable MiniTiles that have their Altitude() equal to 0.
    public boolean Sea() {
        return (m_altitude.intValue() == 0);
    }

	// Lake-MiniTiles are unwalkable MiniTiles that have their Altitude() > 0.
	// They form small zones (inside Terrain-zones) that can be eaysily walked around (e.g. Starcraft's doodads)
	// The intent is to preserve the continuity of altitudes inside Areas.
    public boolean Lake() {
        return (m_altitude.intValue() != 0 && !Walkable());
    }

    // Terrain MiniTiles are just walkable MiniTiles
    public boolean Terrain() {
        return Walkable();
    }

	// For Sea and Lake MiniTiles, returns 0
	// For Terrain MiniTiles, returns a non zero id:
	//    - if (id > 0), id uniquely identifies the Area A that contains this MiniTile.
	//      Moreover we have: A.Id() == id and Map::GetArea(id) == A
	//      For more information about positive Area::ids, see Area::Id()
	//    - if (id < 0), then this MiniTile is part of a Terrain-zone that was considered too small to create an Area for it.
	//      Note: negative Area::ids start from -2
	// Note: because of the lakes, Map::GetNearestArea should be prefered over Map::GetArea.
    public AreaId AreaId() {
        return m_areaId;
    }

    public void SetWalkable(boolean walkable) {
        m_areaId = walkable ? new AreaId(-1) : new AreaId(0);
        m_altitude = walkable ? new Altitude(-1) : new Altitude(1);
    }

    public boolean SeaOrLake() {
        return (m_altitude.intValue() == 1);
    }

    public void SetSea() {
//        { bwem_assert(!Walkable() && SeaOrLake()); m_altitude = 0; }
        if (!(!Walkable() && SeaOrLake())) {
            throw new IllegalStateException();
        }
        m_altitude = new Altitude(0);
    }

    public void SetLake() {
//        { bwem_assert(!Walkable() && Sea()); m_altitude = -1; }
        if (!(!Walkable() && Sea())) {
            throw new IllegalStateException();
        }
        m_altitude = new Altitude(-1);
    }

    public boolean AltitudeMissing() {
        return (m_altitude.intValue() == -1);
    }

    public void SetAltitude(Altitude a) {
//        { bwem_assert_debug_only(AltitudeMissing() && (a > 0)); m_altitude = a; }
        if (!(AltitudeMissing() && a.intValue() > 0)) {
            throw new IllegalStateException();
        }
        m_altitude = new Altitude(a);
    }

    public boolean AreaIdMissing() {
        return (m_areaId.intValue() == -1);
    }

    public void SetAreaId(AreaId id) {
//        { bwem_assert(AreaIdMissing() && (id >= 1)); m_areaId = id; }
        if (!(AreaIdMissing() && id.intValue() >= 1)) {
            throw new IllegalStateException();
        }
        m_areaId = new AreaId(id);
    }

    public void ReplaceAreaId(AreaId id) {
//        { bwem_assert((m_areaId > 0) && ((id >= 1) || (id <= -2)) && (id != m_areaId)); m_areaId = id; }
        if (!((m_areaId.intValue() > 0) && ((id.intValue() >= 1) || (id.intValue() <= -2)) && (!id.equals(m_areaId)))) {
            throw new IllegalStateException();
        }
        m_areaId = new AreaId(id);
    }

    public void SetBlocked() {
//        { bwem_assert(AreaIdMissing()); m_areaId = blockingCP; }
        if (!AreaIdMissing()) {
            throw new IllegalStateException();
        }
        m_areaId = new AreaId(MiniTile.blockingCP);
    }

    public boolean Blocked() {
        return m_areaId.equals(blockingCP);
    }

    public void ReplaceBlockedAreaId(AreaId id) {
//        { bwem_assert((m_areaId == blockingCP) && (id >= 1)); m_areaId = id; }
        if (!(m_areaId.equals(MiniTile.blockingCP) && id.intValue() >= 1)) {
            throw new IllegalStateException();
        }
        m_areaId = new AreaId(id);
    }

}