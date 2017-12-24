package cn.strongme;

import cn.strongme.main.BasicApplication;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BasicApplication.class)
public class BasicApplicationTests {

	private static final String BASE = "http://strongme.s1.natapp.cc";

	@Autowired
	private WxMpService wxMpService;

	@Test
	public void createWechatMenu() throws WxErrorException {

		WxMenu menu = new WxMenu();

		WxMenuButton btnOpenGate = new WxMenuButton();
		btnOpenGate.setName("打开大门");
		btnOpenGate.setType(WxConsts.MenuButtonType.VIEW);
		btnOpenGate.setUrl(wxMpService.oauth2buildAuthorizationUrl(BASE + "/wechat/gate/index", "snsapi_userinfo", ""));

		WxMenuButton btnInviteOpenGate = new WxMenuButton();
		btnInviteOpenGate.setName("邀请开门");
		btnInviteOpenGate.setType(WxConsts.MenuButtonType.VIEW);
		btnInviteOpenGate.setUrl(wxMpService.oauth2buildAuthorizationUrl(BASE + "/wechat/gate/invite", "snsapi_userinfo", ""));

		menu.getButtons().add(btnOpenGate);
		menu.getButtons().add(btnInviteOpenGate);

		wxMpService.getMenuService().menuDelete();
		wxMpService.getMenuService().menuCreate(menu);

	}

}
