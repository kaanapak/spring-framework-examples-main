/**
 * Adds three markers and alters the view bounds of the map to ensure that all
 * markers are visible
 *
 * @param  {H.Map} map      A HERE Map instance within the application
 */
function addMarkersAndSetViewBounds() {
  // create map objects
  var toronto = new H.map.Marker({lat:43.7,  lng:-79.4}),
      boston = new H.map.Marker({lat:42.35805, lng:-71.0636}),
      washington = new H.map.Marker({lat:38.8951, lng:-77.0366}),
      group = new H.map.Group();

  // add markers to the group
  group.addObjects([toronto, boston, washington]);
  map.addObject(group);

  // get geo bounding box for the group and set it to the map
  map.getViewModel().setLookAtData({
    bounds: group.getBoundingBox()
  });
}

/**
 * Boilerplate map initialization code starts below:
 */

//Step 1: initialize communication with the platform
// In your own code, replace variable window.apikey with your own apikey
