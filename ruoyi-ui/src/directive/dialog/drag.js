/**
* v-dialogDrag The window pulls.
* Copyright (c) 2019 ruoyi
*/

export default {
  bind(el, binding, vnode, oldVnode) {
    const value = binding.value
    if (value == false) return
    // Take the head to draw the content.
    const dialogHeaderEl = el.querySelector('.el-dialog__header');
    const dragDom = el.querySelector('.el-dialog');
    dialogHeaderEl.style.cursor = 'move';
    // obtaining original properties. ie domThe Elements.currentStyle Firefighters of Google window.getComputedStyle(domThe Elements, null);
    const sty = dragDom.currentStyle || window.getComputedStyle(dragDom, null);
    dragDom.style.position = 'absolute';
    dragDom.style.marginTop = 0;
    let width = dragDom.style.width;
    if (width.includes('%')) {
      width = +document.body.clientWidth * (+width.replace(/\%/g, '') / 100);
    } else {
      width = +width.replace(/\px/g, '');
    }
    dragDom.style.left = `${(document.body.clientWidth - width) / 2}px`;
    // The mouse presses the event.
    dialogHeaderEl.onmousedown = (e) => {
      // Press the mouse.，Calculate the distance of the current element from the visible area (The mouse click the location from the visible window.)
      const disX = e.clientX - dialogHeaderEl.offsetLeft;
      const disY = e.clientY - dialogHeaderEl.offsetTop;

      // Values obtained.px Rules match replacement.
      let styL, styT;

      // Attention toiein The first value obtained is the component itself.50% After moving the value.px
      if (sty.left.includes('%')) {
        styL = +document.body.clientWidth * (+sty.left.replace(/\%/g, '') / 100);
        styT = +document.body.clientHeight * (+sty.top.replace(/\%/g, '') / 100);
      } else {
        styL = +sty.left.replace(/\px/g, '');
        styT = +sty.top.replace(/\px/g, '');
      };

      // The mouse pulled the incident.
      document.onmousemove = function (e) {
        // Committed by the incident.，Calculate the distance. （Start pulling to the end of pulling distance.）
        const l = e.clientX - disX;
        const t = e.clientY - disY;

        let finallyL = l + styL
        let finallyT = t + styT

        // Moving the current element.
        dragDom.style.left = `${finallyL}px`;
        dragDom.style.top = `${finallyT}px`;

      };

      document.onmouseup = function (e) {
        document.onmousemove = null;
        document.onmouseup = null;
      };
    }
  }
};