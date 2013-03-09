function addEvent(a,b,c){return (a.addEventListener)?a.addEventListener(b,c,false):(a.attachEvent)?a.attachEvent('on'+b,c):false;}

function getBackTop(a){
var x=a.style.backgroundPosition;
if(typeof(x)==='undefined'||x=='')x='0px 0px';
x=parseInt(x.split(' ')[1].replace('px',''));
if(isNaN(x))x=0;
return x;
}

function getBackLeft(a){
var x=a.style.backgroundPosition;
if(typeof(x)==='undefined'||x=='')x='0px 0px';
x=parseInt(x.split(' ')[0].replace('px',''));
if(isNaN(x))x=0;
return x;
}

function move(a,b,c,d,e){
//a is the node.
//b is the final left in px
//c is the final top in px
//[d] is the time that the animation should take,in milliseconds.
//[e] is the function to launch when the animation completed.
if(typeof(d)!='number')var d=200;
if(typeof(e)!='function')var e=function(){};
if(a.getAttribute('movestatus')!=null){clearInterval(a.getAttribute('movestatus'));a.removeAttribute('movestatus');}
var ple=getBackLeft(a);
var pto=getBackTop(a);
if(typeof(b)=='number'&&b!=ple){var le=b;var dle=le-ple;}
else var le=false;
if(typeof(c)=='number'&&c!=pto){var to=c;var dto=to-pto;}
else var to=false;
var end=function(){if(le!==false){a.style.left=le+'px';}if(to!==false){a.style.backgroundPosition='0px '+to.toFixed(0)+'px';}clearInterval(a.getAttribute('movestatus'));a.removeAttribute('movestatus');e();}
var oti=new Date().getTime();
if(to!==false)a.setAttribute('movestatus',setInterval(function(){var ti=new Date().getTime()-oti;if(ti>=d){end();return;}var p=ti/d;p=p*(1+(0.5*(1-p)));a.style.backgroundPosition='0px '+(pto+(dto*p)).toFixed(0)+'px';},10));
else end();
}

var x=setInterval(function(){
if(!document.getElementById('back1'))return;
var o=(window.pageYOffset) ? window.pageYOffset : document.documentElement.scrollTop;
document.getElementById('back1').style.backgroundPosition='0px '+(o*-0.5).toFixed(0)+'px';
document.getElementById('back2').style.backgroundPosition='0px '+(o*-0.1).toFixed(0)+'px';
setTimeout(function(){window.onscroll=function(){
	var o=(window.pageYOffset) ? window.pageYOffset : document.documentElement.scrollTop;
	move(document.getElementById('back1'),null,o*-0.5);
	move(document.getElementById('back2'),null,o*-0.1);
	};
},1);
clearInterval(x);
},10);
