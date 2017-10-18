/*
Status: Incomplete
*/

package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.Unit;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Mineral
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Minerals Correspond to the units in BWAPI::getStaticNeutralUnits() for which getType().isMineralField(),
//
//////////////////////////////////////////////////////////////////////////////////////////////

public final class Mineral extends Resource {

    public Mineral(Unit u, Map pMap) {
        super(u, pMap);

//        bwem_assert(Type().isMineralField());
        if (!(u instanceof MineralPatch)) {
            throw new IllegalArgumentException("Unit is not a MineralPatch: " + u.getClass().getName());
        }
    }

    //TODO:
    //public:
//    Mineral::~Mineral()
//    {
//        MapImpl::Get(GetMap())->OnMineralDestroyed(this);
//    }

    @Override
    public int InitialAmount() {
        MineralPatch ret = (MineralPatch) super.Unit();
        return ret.getInitialResources();
    }

    @Override
    public int Amount() {
        MineralPatch ret = (MineralPatch) super.Unit();
        return ret.getResources();
    }

}
