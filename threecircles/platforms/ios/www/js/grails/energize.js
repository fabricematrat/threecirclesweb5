(function(){if(!("ontouchstart"in window&&!/chrome/i.test(navigator.userAgent)))return;var e={};var t=function(e,t){return Math.abs(e[0]-t[0])>5||Math.abs(e[1]-t[1])>5};var n=function(e){this.startXY=[e.touches[0].clientX,e.touches[0].clientY];this.threshold=false};var r=function(e){if(this.threshold)return false;this.threshold=t(this.startXY,[e.touches[0].clientX,e.touches[0].clientY])};var i=function(e){if(this.threshold||t(this.startXY,[e.changedTouches[0].clientX,e.changedTouches[0].clientY])){return}var n=e.changedTouches[0],r=document.createEvent("MouseEvents");r.initMouseEvent("click",true,true,window,0,n.screenX,n.screenY,n.clientX,n.clientY,false,false,false,false,0,null);r.simulated=true;e.target.dispatchEvent(r)};var s=function(t){var n=Date.now(),r=n-e.time,i=t.clientX,s=t.clientY,u=[Math.abs(e.x-i),Math.abs(e.y-s)],a=o(t.target,"A")||t.target,f=a.nodeName,l=f==="A",c=window.navigator.standalone&&l&&t.target.getAttribute("href");e.time=n;e.x=i;e.y=s;if(!t.simulated&&(r<500||r<1500&&u[0]<50&&u[1]<50)||c){t.preventDefault();t.stopPropagation();if(!c)return false}if(c){window.location=a.getAttribute("href")}if(!a||!a.classList)return;a.classList.add("energize-focus");window.setTimeout(function(){a.classList.remove("energize-focus")},150)};var o=function(e,t){var n=e,r=t.toUpperCase();while(n!==document.body){if(!n||n.nodeName===r){return n}n=n.parentNode}return null};document.addEventListener("touchstart",n,false);document.addEventListener("touchmove",r,false);document.addEventListener("touchend",i,false);document.addEventListener("click",s,true)})()