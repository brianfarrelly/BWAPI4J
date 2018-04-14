////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published 
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

package bwem.map;

import bwem.CheckMode;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public interface AdvancedDataInitializer {

    Tile getTile_(TilePosition tilePosition, CheckMode checkMode);

    Tile getTile_(TilePosition tilePosition);

    MiniTile getMiniTile_(WalkPosition walkPosition, CheckMode checkMode);

    MiniTile getMiniTile_(WalkPosition walkPosition);

    void markUnwalkableMiniTiles(BWMap bwMap);

    void markBuildableTilesAndGroundHeight(BWMap bwMap);

    void decideSeasOrLakes(int lakeMaxMiniTiles, int lakeMaxWidthInMiniTiles);

}
