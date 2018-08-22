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

#pragma once

#include <BWAPI.h>
#include <jni.h>

class BridgeData {
 public:
  BridgeData();

  void reset();

  int getIndex() const;

  void add(const int val);

  void add(const BWAPI::TilePosition &tilePosition);
  void add(const BWAPI::WalkPosition &walkPosition);
  void add(const BWAPI::Position &position);
  void add(const BWAPI::UnitType &unitType);
  void add(const BWAPI::Unit &unit);
  void add(const BWAPI::Player &player);

  static const size_t intBufSize = 5000000U;
  jint intBuf[intBufSize];

 private:
  int _index;
};
