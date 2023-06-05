import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import {MY_GITHUB} from "@/pages/contants/indx";
const Footer: React.FC = () => {
  const defaultMessage = 'Try to best 团队制作';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'BiliBili',
          title: 'Try To Best',
          href: 'https://www.bilibili.com',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <><GithubOutlined /> Try to best github</>,
          href: MY_GITHUB,
          blankTarget: true,
        },
        {
          key: 'Ant Design',
          title: 'Ant Design',
          href: 'https://ant.design',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
