(function($){

$.event.special.hoverintent = {
    setup: function() {
      $( this ).bind( "mouseover", jQuery.event.special.hoverintent.handler );
    },
    teardown: function() {
      $( this ).unbind( "mouseover", jQuery.event.special.hoverintent.handler );
    },
    handler: function( event ) {
		var currentX, currentY, timeout,
		args = arguments,
		target = $( event.target ),
		previousX = event.pageX,
		previousY = event.pageY;

		function track( event ) {
			currentX = event.pageX;
			currentY = event.pageY;
		};

		function clear() {
		target
		  .unbind( "mousemove", track )
		  .unbind( "mouseout", clear );
		clearTimeout( timeout );
		}

		function handler() {
		var prop,
		  orig = event;

		if ( ( Math.abs( previousX - currentX ) +
			Math.abs( previousY - currentY ) ) < 7 ) {
		  clear();

		  event = $.Event( "hoverintent" );
		  for ( prop in orig ) {
			if ( !( prop in event ) ) {
			  event[ prop ] = orig[ prop ];
			}
		  }
		  delete event.originalEvent;

		  target.trigger( event );
		} else {
		  previousX = currentX;
		  previousY = currentY;
		  timeout = setTimeout( handler, 50 );
		}
		}

		timeout = setTimeout( handler, 50 );
		target.bind({
			mousemove: track,
			mouseout: clear
		});
    }
  };

})(window.jQuery)