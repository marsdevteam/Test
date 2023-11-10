import SideBar from './SideBar';
import { Menu } from '../data/Menu'

function Home() {
const menuList = Menu()

    return <>
        <SideBar data = {menuList}/>
    </>
}

export default Home;