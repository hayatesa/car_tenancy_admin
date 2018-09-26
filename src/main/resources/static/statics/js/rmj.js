/**
 * 简易视图渲染
 */

/**
 * 视图与数据双向绑定
 */
function r_bind(app) {
    let _data = app._r_data;
    let _options = app._r_options;
    $(_options.el + ' [r-bind]').each((index, element) => {
        let el = $(element);
        let property = el.attr('r-bind');
        // 设置属性
        // eval('(app.' + property +'=_data.' + property + ')');
        // 视图渲染
        var initValue = eval('(_data.'+ property +')');
        el.val(initValue);
        el.on('change', () => {
            let value = el.val();
            let cmd = '(' + '_data.' + property + '="' + value + '")';
            eval(cmd);
        });
        var preProperty = property.substr(0, property.lastIndexOf('.'));
        var postProperty = property.substr(property.lastIndexOf('.') + 1);
        // 监听对象
        Object.defineProperty(eval('('+'_data.'+preProperty+')'), postProperty, {
            get: () => {
                return el.val();
            },
            set: (value) => {
                $('[r-bind="' + property +'"]').val(value);
            }
        })
    });
}

function Rmj(options) {
    options.init();
    this._r_options = options;
    this._r_data = options.data;
    this._r_methods = options.methods;
    r_bind(this);

}
