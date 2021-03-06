// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License.
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

package bwem.area;

import bwem.ChokePoint;
import bwem.Markable;
import bwem.area.typedef.GroupId;
import bwem.map.Map;
import bwem.map.TerrainData;
import bwem.tile.Tile;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import java.util.List;
import org.openbw.bwapi4j.TilePosition;

public interface AreaInitializer {
  Markable getMarkable();

  void addChokePoints(Area area, List<ChokePoint> chokePoints);

  void addMineral(final Mineral mineral);

  void addGeyser(Geyser geyser);

  // Called for each tile t of this Area
  void addTileInformation(TilePosition tilePosition, Tile tile);

  void setGroupId(GroupId gid);

  Map getMap();

  // Called after addTileInformation(t) has been called for each tile t of this Area
  void postCollectInformation();

  int[] computeDistances(ChokePoint startCP, List<ChokePoint> targetCPs);

  // Returns Distances such that Distances[i] == ground_distance(start, targets[i]) in pixels
  // Note: same algorithm than Graph::computeDistances (derived from Dijkstra)
  int[] computeDistances(TilePosition start, List<TilePosition> targets);

  void updateAccessibleNeighbors();

  // Fills in bases with good locations in this Area.
  // The algorithm repeatedly searches the best possible location L (near resources)
  // When it finds one, the nearby resources are assigned to L, which makes the remaining resources
  // decrease.
  // This causes the algorithm to always terminate due to the lack of remaining resources.
  // To efficiently compute the distances to the resources, with use Potiential Fields in the
  // InternalData() value of the Tiles.
  void createBases(TerrainData terrainData);

  // Calculates the score >= 0 corresponding to the placement of a Base Command center at
  // 'location'.
  // The more there are resources nearby, the higher the score is.
  // The function assumes the distance to the nearby resources has already been computed (in
  // InternalData()) for each tile around.
  // The job is therefore made easier : just need to sum the InternalData() values.
  // Returns -1 if the location is impossible.
  int computeBaseLocationScore(TerrainData terrainData, TilePosition location);

  // Checks if 'location' is a valid location for the placement of a Base Command center.
  // If the location is valid except for the presence of Mineral patches of less than 9 (see
  // Andromeda.scx),
  // the function returns true, and these minerals are reported in blockingMinerals
  // The function is intended to be called after computeBaseLocationScore, as it is more expensive.
  // See also the comments inside computeBaseLocationScore.
  boolean validateBaseLocation(
      TerrainData terrainData, TilePosition location, List<Mineral> blockingMinerals);
}
