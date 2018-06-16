# Oyodo

#### A parser library for kantai collection(艦これ). 

- Written in Kotlin, 100% interoperable with Java.
- Use RxJava to provide data.
- Used by [KanColleCommand](https://github.com/lhc-clover/KanColleCommand) - A browser for KanColle android.

#### Usage

Call `attention()` to obtain instance

```
Oyodo.attention()
```

Init before using Oyodo, `api_start2` file is needed for raw data(ship and item)

```
Oyodo.attention().init("path/to/api_start2/file")
```
`checkStart()` returns true if file is parsed successfully

Dispatch ***unchunked*** kcsapi data to Oyodo

```
Oyodo.attention().api(url, requestBody, responseBody)
```

Register to Oyodo to subscribe data

```
Kotlin

Oyodo.attention().watch(User.nickname, { /** Called when data changed */ })

```
```
Java

Oyodo.Companion.attention().watch(User.INSTANCE.getNickname(), new Watcher<String>() {
            @Override
            public void onChange(String s) {
                /** Called when data changed */
            }
        });
```

You should got data immediately if observable already holds one, and be notified everytime data changed.   

#### Observer list:   

- Battle
	- phase : battle progress like `Start`, `PracticeNight` and `BattleResult`
- Dock
	- expeditionList
	- buildList
	- repairList
- Fleet
	- deckShipIds
	- deckNames
	- shipWatcher : An observable map or a map of observables are both bad practice. Oyodo only notify changed ship id list.
	- slotWatcher : Same as above.
- Mission
	- questMap
- Resource
	- fuel
	- ammo
	- metal
	- bauxite
	- bucket
	- burner
	- research
	- improve
- User
	- nickname
	- level
	- shipCount
	- shipMax
	- slotCount
	- slotMax
	- kDockCount
	- nDockCount
	- deckCount

#### Other

- `rawShipMap` and `rawSlotMap` in class `Raw` are for raw data parsed from `api_start2`.     
- `shipMap` and `slotMap` in class `Fleet` are for port data.
- More battle(出撃) and practice(演習) info are provided by `Battle`.

#### Thanks
* [poi](https://github.com/poooi/poi) - Scalable KanColle browser and tool.
* [kcanotify](https://github.com/antest1/kcanotify) - Viewer Application for KanColle Android


#### Copyright
	This program is free software: you can redistribute it and/or modify   
	it under the terms of the GNU Lesser General Public License as   
	published by the Free Software Foundation, either version 3 of the 
	License, or (at your option) any later version.
	
	This program is distributed in the hope that it will be useful,   
	but WITHOUT ANY WARRANTY; without even the implied warranty of   
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the   
	GNU Lesser General Public License for more details.
	
	You should have received a copy of the GNU Lesser General Public 
	License along with this program.  If not, see <http://www.gnu.org/licenses/>.